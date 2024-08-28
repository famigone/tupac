import { Button } from '@hilla/react-components/Button.js';
import { Notification } from '@hilla/react-components/Notification.js';
import { TextField } from '@hilla/react-components/TextField.js';
import { useEffect, useState } from 'react';
import { Grid } from "@hilla/react-components/Grid";
import { GridColumn } from "@hilla/react-components/GridColumn";
import { Card, CardHeader, CardBody, CardFooter } from '@chakra-ui/react'
import PerfilRecord from 'Frontend/generated/com/example/application/services/PerfilService/PerfilRecord';
import { PerfilService } from 'Frontend/generated/endpoints';
import { Divider } from '@chakra-ui/react'
import { ConfirmDialog } from '@hilla/react-components/ConfirmDialog.js';
import { HorizontalLayout } from '@hilla/react-components/HorizontalLayout.js';
import { Icon } from '@hilla/react-components/Icon.js';
import { GridSortColumn } from '@hilla/react-components/GridSortColumn.js';
import { GridFilterColumn } from '@hilla/react-components/GridFilterColumn.js';
import PerfilForm from './PerfilForm';
import { Heading, Text } from '@chakra-ui/react'
import { useNavigate } from 'react-router-dom'; // Importa useNavigate
export default function HomePerfil() {
  const [Perfiles, setPerfiles] = useState<PerfilRecord[]>([]);
  const [selected, setSelected] = useState<PerfilRecord | null>();
  const [dialogOpened, setDialogOpened] = useState(false);
  const [deleteHabilitado, setDeleteHabilitado] = useState(true);
  const navigate = useNavigate();
  useEffect(() => {
    PerfilService.findAllPerfiles().then(setPerfiles)
    
  }, []);


  const onPerfilDeleted = async () => {
    if (selected && selected.id) {
      try {
        // Llamar al servicio para eliminar el registro
        await PerfilService.delete(selected.id);
        //actualizamos el estado          
        setPerfiles(Perfiles.filter(Perfil => Perfil.id != selected.id))
      } catch (error) {
        console.error("Error al eliminar el Perfil:", error);
      }
    }
  };

  async function onPerfilSaved(Perfil: PerfilRecord) {
    
    const saved = await PerfilService.save(Perfil)
    if (Perfil.id) {
      setPerfiles(Perfiles => Perfiles.map(current => current.id === saved.id ? saved : current));
    } else {
      setPerfiles(Perfiles => [...Perfiles, saved]);
    }
    setSelected(saved);
  }

  return (
    <>
      <div className="p-m  gap-m border: 2px">

        <Card>
          <CardBody>
            <Heading mb={2} size='sm'>NUEVO ESTUDIANTE</Heading>
            <Divider />
            <PerfilForm
              Perfil={selected}
              onSubmit={onPerfilSaved}
            />
          </CardBody>
        </Card>
      </div>
      <div className="p-m  gap-m">
        <Card>
          <CardBody>
            <Heading mb={2} size='sm'>ESTUDIANTES</Heading>
            <Divider />
            <Grid
              theme="row-stripes"
              allRowsVisible
              items={Perfiles}
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
              

            </div>
          </CardBody>
        </Card>
        <ConfirmDialog
          header="Desea eliminar la Perfil?"
          cancelButtonVisible
          confirmText="Eliminar"
          cancelText="Cancelar"
          opened={dialogOpened}
          onConfirm={() => {
            onPerfilDeleted()
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