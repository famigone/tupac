import { Button } from '@hilla/react-components/Button.js';
import { Notification } from '@hilla/react-components/Notification.js';
import { TextField } from '@hilla/react-components/TextField.js';
import { useEffect, useState } from 'react';
import { Grid } from "@hilla/react-components/Grid";
import { GridColumn } from "@hilla/react-components/GridColumn";
import { Card, CardHeader, CardBody, CardFooter } from '@chakra-ui/react'
import PracticoRecord from 'Frontend/generated/com/example/application/services/PracticoService/PracticoRecord';
import { MateriaService, PracticoService } from 'Frontend/generated/endpoints';
import { Divider } from '@chakra-ui/react'
import { ConfirmDialog } from '@hilla/react-components/ConfirmDialog.js';
import { HorizontalLayout } from '@hilla/react-components/HorizontalLayout.js';
import { Icon } from '@hilla/react-components/Icon.js';
import { GridSortColumn } from '@hilla/react-components/GridSortColumn.js';
import { GridFilterColumn } from '@hilla/react-components/GridFilterColumn.js';
import PracticoForm from './PracticoForm';
import { Heading, Text } from '@chakra-ui/react'
import { useParams } from 'react-router-dom';
import Materia from 'Frontend/generated/com/example/application/model/Materia';


export default function HomePractico() {
  const [Practicos, setPracticos] = useState<PracticoRecord[]>([]);
  const [selected, setSelected] = useState<PracticoRecord | null>();
  const [dialogOpened, setDialogOpened] = useState(false);
  const [deleteHabilitado, setDeleteHabilitado] = useState(true);
  const [materia, setMateria] = useState<Materia | undefined>(undefined);
  

  //const { materiaid } = useParams();
  const { materiaid } = useParams<{ materiaid: string }>();


  useEffect(() => {    
    if (materiaid) {      
      var materiaidNumber = parseInt(materiaid, 10);
      if (!isNaN(materiaidNumber)) {        
        PracticoService.findPracticoByMateriaid(materiaidNumber).then(setPracticos);
      } else {
        console.error("El ID de la materia no es un número válido");
      }
    }
  }, [materiaid]);



  
  
  const onPracticoDeleted = async () => {
    if (selected && selected.id) {
      try {
        // Llamar al servicio para eliminar el registro
        await PracticoService.delete(selected.id);
        //actualizamos el estado          
        setPracticos(Practicos.filter(Practico => Practico.id != selected.id))
      } catch (error) {
        console.error("Error al eliminar el Practico:", error);
      }
    }
  };

  async function onPracticoSaved(Practico: PracticoRecord) {
    console.log("materiaid esssss "+materiaid)  
    if (materiaid) {      
      console.log("materiaid esssss "+materiaid)  
      Practico.materiaid = parseInt(materiaid, 10);
      console.log("id materia en práctico: "+Practico.materiaid)
    }  
    const saved = await PracticoService.save(Practico)
    if (Practico.id) {
      setPracticos(Practicos => Practicos.map(current => current.id === saved.id ? saved : current));
    } else {
      setPracticos(Practicos => [...Practicos, saved]);
    }
    setSelected(saved);
  }

  return (
    <>
      <div className="p-m  gap-m border: 2px">

        <Card>
          <CardBody>
            <Heading mb={2} size='sm'>Nuevo Practico</Heading>
            <Divider />
            <PracticoForm
              Practico={selected}
              onSubmit={onPracticoSaved}
            />
          </CardBody>
        </Card>
      </div>
      <div className="p-m  gap-m">
        <Card>
          <CardBody>
            <Heading mb={2} size='sm'>PRÁCTICOS</Heading>
            <Divider />
            <Grid
              theme="row-stripes"
              allRowsVisible
              items={Practicos}
              onActiveItemChanged={e => setSelected(e.detail.value)}
              selectedItems={[selected]}>
              <GridFilterColumn path="nombre" header="NOMBRE" />
              <GridFilterColumn path="descripcion" header="DESCRIPCIÓN" />
              <GridFilterColumn path="desde" header="DESDE" />
              <GridFilterColumn path="hasta" header="HASTA" />
            </Grid>
            <Divider />
            <div style={{ margin: '3px' }} className="flex gap-m gap-s">
            <Button 
                onClick={() => setSelected(null)} theme="secondary small" >                 
                 Nuevo
              </Button>
              <Button disabled={selected == null} theme="secondary  error small" onClick={() => setDialogOpened(true)} >
                Eliminar
              </Button>
              
              <Button 
                disabled={selected == null}
                onClick={() => setSelected(null)} theme="primary small" > 
               
                Trabajos Prácticos
              </Button>
            </div>
          </CardBody>
        </Card>
        <ConfirmDialog
          header="Desea eliminar la Practico?"
          cancelButtonVisible
          confirmText="Eliminar"
          cancelText="Cancelar"
          opened={dialogOpened}
          onConfirm={() => {
            onPracticoDeleted()
            setDialogOpened(false)
          }}
          onCancel={() => {
            setDialogOpened(false)
          }}
        />

      </div>
    </>
  );
}