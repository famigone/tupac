import { Button } from '@hilla/react-components/Button.js';
import { Notification } from '@hilla/react-components/Notification.js';
import { TextField } from '@hilla/react-components/TextField.js';
import { useEffect, useState } from 'react';
import { Grid } from "@hilla/react-components/Grid";
import { GridColumn } from "@hilla/react-components/GridColumn";
import { Card, CardHeader, CardBody, CardFooter } from '@chakra-ui/react'
import MateriaRecord from 'Frontend/generated/com/example/application/services/MateriaService/MateriaRecord';
import { MateriaService } from 'Frontend/generated/endpoints';
import { Divider } from '@chakra-ui/react'
import { ConfirmDialog } from '@hilla/react-components/ConfirmDialog.js';
import { HorizontalLayout } from '@hilla/react-components/HorizontalLayout.js';
import { Icon } from '@hilla/react-components/Icon.js';
import { GridSortColumn } from '@hilla/react-components/GridSortColumn.js';
import { GridFilterColumn } from '@hilla/react-components/GridFilterColumn.js';
import MateriaForm from './MateriaForm';
import { Heading, Text } from '@chakra-ui/react'
import { useNavigate } from 'react-router-dom'; // Importa useNavigate
export default function HomeMateria() {
  const [Materias, setMaterias] = useState<MateriaRecord[]>([]);
  const [selected, setSelected] = useState<MateriaRecord | null>();
  const [dialogOpened, setDialogOpened] = useState(false);
  const [deleteHabilitado, setDeleteHabilitado] = useState(true);
  const navigate = useNavigate();
  useEffect(() => {
    MateriaService.findAllMaterias().then(setMaterias)
    console.log(setMaterias)
  }, []);


  const onMateriaDeleted = async () => {
    if (selected && selected.id) {
      try {
        // Llamar al servicio para eliminar el registro
        await MateriaService.delete(selected.id);
        //actualizamos el estado          
        setMaterias(Materias.filter(Materia => Materia.id != selected.id))
      } catch (error) {
        console.error("Error al eliminar el Materia:", error);
      }
    }
  };

  async function onMateriaSaved(Materia: MateriaRecord) {
    
    const saved = await MateriaService.save(Materia)
    if (Materia.id) {
      setMaterias(Materias => Materias.map(current => current.id === saved.id ? saved : current));
    } else {
      setMaterias(Materias => [...Materias, saved]);
    }
    setSelected(saved);
  }

  return (
    <>
      <div className="p-m  gap-m border: 2px">

        <Card>
          <CardBody>
            <Heading mb={2} size='sm'>NUEVA MATERIA</Heading>
            <Divider />
            <MateriaForm
              Materia={selected}
              onSubmit={onMateriaSaved}
            />
          </CardBody>
        </Card>
      </div>
      <div className="p-m  gap-m">
        <Card>
          <CardBody>
            <Heading mb={2} size='sm'>MATERIAS</Heading>
            <Divider />
            <Grid
              theme="row-stripes"
              allRowsVisible
              items={Materias}
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
                onClick={() => {
                  if (selected) {
                    navigate(`/practico/${selected.id}`); // Navega a la URL con el id de la materia
                  }
                }} theme="primary small">
                Trabajos Prácticos
              </Button>
            </div>
          </CardBody>
        </Card>
        <ConfirmDialog
          header="Desea eliminar la Materia?"
          cancelButtonVisible
          confirmText="Eliminar"
          cancelText="Cancelar"
          opened={dialogOpened}
          onConfirm={() => {
            onMateriaDeleted()
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