import { TextField } from "@hilla/react-components/TextField";
import { Select, SelectItem } from "@hilla/react-components/Select";
import { Button } from "@hilla/react-components/Button";
import { useForm, useFormPart } from "@hilla/react-form";
import { MateriaService } from "Frontend/generated/endpoints";
import { useEffect, useState } from "react";
import MateriaRecord from "Frontend/generated/com/example/application/services/MateriaService/MateriaRecord";
import MateriaRecordModel from "Frontend/generated/com/example/application/services/MateriaService/MateriaRecordModel";
import { Icon } from "@hilla/react-components/Icon.js";
import { NotEmpty } from "@hilla/form/Validators.js";
import { DatePicker, DatePickerElement } from "@hilla/react-components/DatePicker.js";
import { DateTimePicker } from "@hilla/react-components/DateTimePicker.js";

interface MateriaFormProps {
    Materia?: MateriaRecord | null;
    onSubmit?: (Materia: MateriaRecord) => Promise<void>;
}

export default function MateriaForm({ Materia, onSubmit }: MateriaFormProps) {

    const [Materias, setMaterias] = useState<SelectItem[]>([]);

    const { field, model, submit, reset, read } = useForm(MateriaRecordModel, { onSubmit });
    const eliminarMateria = async () => {
        if (Materia && Materia.id) {
            try {
                // Llamar al servicio para eliminar el registro
                await MateriaService.delete(Materia.id);

                // Si la eliminación es exitosa, ejecutar la función onSubmit para actualizar el estado de la lista de Materiaes
                if (onSubmit) {
                    await onSubmit(Materia);
                }

                // Limpiar el formulario después de eliminar el registro
                reset();
            } catch (error) {
                console.error("Error al eliminar el Materia:", error);
            }
        }
    };

    useEffect(() => {
        read(Materia);
    }, [Materia]);

    //control de vacíos del lado del cliente

    const nombre = useFormPart(model.nombre);



    useEffect(() => {
        nombre.addValidator(
            new NotEmpty({
                message: 'Por favor, ingrese un nombre'
            }));

    }, []);


    return (
        <>

            <div className="flex gap-s items-start">
                
                <TextField label="Nombre" {...field(model.nombre)} />
                <TextField label="Descripción" {...field(model.descripcion)} />
                <DateTimePicker label="Desde" {...field(model.desde)} />
                <DateTimePicker label="Desde" {...field(model.hasta)} />
                

            </div>

            <div className="flex gap-m"  >
                <Button onClick={submit} theme="primary small"> <Icon icon="vaadin:arrow-circle-down" />
                    Guardar</Button>

            </div>

        </>
    );

}