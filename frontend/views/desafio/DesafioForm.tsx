import { TextField } from "@hilla/react-components/TextField";
import { Select, SelectItem } from "@hilla/react-components/Select";
import { Button } from "@hilla/react-components/Button";
import { useForm, useFormPart } from "@hilla/react-form";
import { DesafioService } from "Frontend/generated/endpoints";
import { useEffect, useState } from "react";
import DesafioRecord from "Frontend/generated/com/example/application/services/DesafioService/DesafioRecord";
import DesafioRecordModel from "Frontend/generated/com/example/application/services/DesafioService/DesafioRecordModel";
import { Icon } from "@hilla/react-components/Icon.js";
import { NotEmpty } from "@hilla/form/Validators.js";
import { DatePicker, DatePickerElement } from "@hilla/react-components/DatePicker.js";
import { DateTimePicker } from "@hilla/react-components/DateTimePicker.js";
import { TextArea } from "@hilla/react-components/TextArea.js";

import { Divider, Grid, GridItem } from '@chakra-ui/react'
import { FormLayout } from "@hilla/react-components/FormLayout.js";

interface DesafioFormProps {
    Desafio?: DesafioRecord | null;
    onSubmit?: (Desafio: DesafioRecord) => Promise<void>;
}

export default function DesafioForm({ Desafio, onSubmit }: DesafioFormProps) {

    const [Desafios, setDesafios] = useState<SelectItem[]>([]);

    const { field, model, submit, reset, read } = useForm(DesafioRecordModel, { onSubmit });
    const eliminarDesafio = async () => {
        if (Desafio && Desafio.id) {
            try {
                // Llamar al servicio para eliminar el registro
                await DesafioService.delete(Desafio.id);

                // Si la eliminación es exitosa, ejecutar la función onSubmit para actualizar el estado de la lista de Desafioes
                if (onSubmit) {
                    await onSubmit(Desafio);
                }

                // Limpiar el formulario después de eliminar el registro
                reset();
            } catch (error) {
                console.error("Error al eliminar el Desafio:", error);
            }
        }
    };

    useEffect(() => {
        read(Desafio);
    }, [Desafio]);

    //control de vacíos del lado del cliente

    const desafio = useFormPart(model.desafio);
    



    useEffect(() => {
        desafio.addValidator(
            new NotEmpty({
                message: 'Por favor, ingrese un desafío'
            }));

    }, []);

    const responsiveSteps = [
        { minWidth: '0', columns: 1 },
        { minWidth: '500px', columns: 2 },
      ];
    return (
        <>



            
            <FormLayout responsiveSteps={responsiveSteps}>                                
                <TextArea label="Descripción" {...field(model.desafio)} />
                <TextField label="Orden" {...field(model.orden)} />           
            </FormLayout>    
            
<Divider/>
            <div className="flex gap-m"  >                
                <Button onClick={submit} theme="secondary small"> 
                    
                    Guardar</Button>

            </div>  
 
        </>
    );

}