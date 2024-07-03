import { Button } from '@hilla/react-components/Button.js';
import { Notification } from '@hilla/react-components/Notification.js';
import { TextField } from '@hilla/react-components/TextField.js';
import { useEffect, useState } from 'react';
import { Grid } from "@hilla/react-components/Grid";
import { GridColumn } from "@hilla/react-components/GridColumn";
import { Card, CardHeader, CardBody, CardFooter } from '@chakra-ui/react'
import DesafioRecord from 'Frontend/generated/com/example/application/services/DesafioService/DesafioRecord';
import { MateriaService, DesafioService } from 'Frontend/generated/endpoints';
import { Divider } from '@chakra-ui/react'
import { ConfirmDialog } from '@hilla/react-components/ConfirmDialog.js';
import { HorizontalLayout } from '@hilla/react-components/HorizontalLayout.js';
import { Icon } from '@hilla/react-components/Icon.js';
import { GridSortColumn } from '@hilla/react-components/GridSortColumn.js';
import { GridFilterColumn } from '@hilla/react-components/GridFilterColumn.js';
import DesafioForm from './DesafioForm';
import { Heading, Text } from '@chakra-ui/react'
import { useParams } from 'react-router-dom';
import Materia from 'Frontend/generated/com/example/application/model/Materia';


export default function HomeDesafio() {
  const [Desafios, setDesafios] = useState<DesafioRecord[]>([]);
  const [selected, setSelected] = useState<DesafioRecord | null>();
  const [dialogOpened, setDialogOpened] = useState(false);
  const [deleteHabilitado, setDeleteHabilitado] = useState(true);
  const [materia, setMateria] = useState<Materia | undefined>(undefined);
  

  //const { materiaid } = useParams();
  const { practicoid } = useParams<{ practicoid: string }>();


  useEffect(() => {    
    if (practicoid) {      
      var practicoidNumber = parseInt(practicoid, 10);
      if (!isNaN(practicoidNumber)) {        
        DesafioService.findDesafioByPracticoid(practicoidNumber).then(setDesafios);
      } else {
        console.error("El ID de la materia no es un número válido");
      }
    }
  }, [practicoid]);



  
  
  const onDesafioDeleted = async () => {
    if (selected && selected.id) {
      try {
        // Llamar al servicio para eliminar el registro
        await DesafioService.delete(selected.id);
        //actualizamos el estado          
        setDesafios(Desafios.filter(Desafio => Desafio.id != selected.id))
      } catch (error) {
        console.error("Error al eliminar el Desafio:", error);
      }
    }
  };

  async function onDesafioSaved(Desafio: DesafioRecord) {
    
    if (practicoid) {      
      
      Desafio.practicoid = parseInt(practicoid, 10);
      
    }  
    const saved = await DesafioService.save(Desafio)
    if (Desafio.id) {
      setDesafios(Desafios => Desafios.map(current => current.id === saved.id ? saved : current));
    } else {
      setDesafios(Desafios => [...Desafios, saved]);
    }
    setSelected(saved);
  }

  return (
    <>
      <div className="p-m  gap-m border: 2px">

        <Card>
          <CardBody>
            <Heading mb={2} size='sm'>Nuevo Desafio</Heading>
            <Divider />
            <DesafioForm
              Desafio={selected}
              onSubmit={onDesafioSaved}
            />
          </CardBody>
        </Card>
      </div>
      <div className="p-m  gap-m">
        <Card>
          <CardBody>
            <Heading mb={2} size='sm'>DESAFIOS</Heading>
            <Divider />
            <Grid
              theme="row-stripes"
              allRowsVisible
              items={Desafios}
              onActiveItemChanged={e => setSelected(e.detail.value)}
              selectedItems={[selected]}>
              <GridFilterColumn path="desafio" header="DESAFIO" />
              <GridFilterColumn path="orden" header="ORDEN" />
              
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
               
                Lote de Pruebas
              </Button>
            </div>
          </CardBody>
        </Card>
        <ConfirmDialog
          header="Desea eliminar la Desafio?"
          cancelButtonVisible
          confirmText="Eliminar"
          cancelText="Cancelar"
          opened={dialogOpened}
          onConfirm={() => {
            onDesafioDeleted()
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