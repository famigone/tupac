import { Button } from '@hilla/react-components/Button.js';
import { Notification } from '@hilla/react-components/Notification.js';
import { TextField } from '@hilla/react-components/TextField.js';
import { useEffect, useState } from 'react';
import { Grid } from "@hilla/react-components/Grid";
import { GridColumn } from "@hilla/react-components/GridColumn";

import MateriaRecord from 'Frontend/generated/com/example/application/services/MateriaService/MateriaRecord';
import { MateriaService } from 'Frontend/generated/endpoints';

import { ConfirmDialog } from '@hilla/react-components/ConfirmDialog.js';
import { HorizontalLayout } from '@hilla/react-components/HorizontalLayout.js';
import { Icon } from '@hilla/react-components/Icon.js';
import { GridSortColumn } from '@hilla/react-components/GridSortColumn.js';
import { GridFilterColumn } from '@hilla/react-components/GridFilterColumn.js';
import MateriaForm from './MateriaForm';

export default function HomeMateria() {
  const [Materias, setMaterias] = useState<MateriaRecord[]>([]);
  const [selected, setSelected] = useState<MateriaRecord | null>();
  const [dialogOpened, setDialogOpened] = useState(false);
  const [deleteHabilitado, setDeleteHabilitado] = useState(true);

  useEffect(() => {
    MateriaService.findAllMaterias().then(setMaterias)
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
    console.log("Materia.id "+Materia.id)
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
            <MateriaForm
                Materia={selected}
                onSubmit={onMateriaSaved}
            />
      </div>
      <div className="p-m  gap-m">
        <Grid
          theme="row-stripes"
          allRowsVisible
          items={Materias}
          onActiveItemChanged={e => setSelected(e.detail.value)}
          selectedItems={[selected]}>
          <GridFilterColumn path="codigo" header="CÓDIGO" />         
          <GridFilterColumn path="nombre" header="NOMBRE" />         
          <GridFilterColumn path="descripcion" header="DESCRIPCIÓN" />         
        </Grid>

        <div style={{ margin: '3px' }} className="flex gap-m gap-s">

          <Button disabled={selected == null} theme="primary error small" onClick={() => setDialogOpened(true)} ><Icon icon="vaadin:close" /> Eliminar</Button>
          <Button onClick={() => setSelected(null)} theme="primary small" ><Icon icon="vaadin:refresh" />
            Nuevo</Button>
          
        </div>
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