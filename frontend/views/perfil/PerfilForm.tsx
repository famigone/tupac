import { TextField } from "@hilla/react-components/TextField";
import { Select, SelectItem } from "@hilla/react-components/Select";
import { Button } from "@hilla/react-components/Button";
import { useForm, useFormPart } from "@hilla/react-form";
import { PerfilService } from "Frontend/generated/endpoints";
import { useEffect, useState } from "react";
import PerfilRecord from "Frontend/generated/com/example/application/services/PerfilService/PerfilRecord";
import PerfilRecordModel from "Frontend/generated/com/example/application/services/PerfilService/PerfilRecordModel";
import { Icon } from "@hilla/react-components/Icon.js";
import { NotEmpty } from "@hilla/form/Validators.js";
import { DatePicker, DatePickerElement } from "@hilla/react-components/DatePicker.js";
import { DateTimePicker } from "@hilla/react-components/DateTimePicker.js";
import { TextArea } from "@hilla/react-components/TextArea.js";
import { Card, Heading, CardHeader, CardBody, CardFooter } from '@chakra-ui/react'
import { Divider, Grid, GridItem } from '@chakra-ui/react'
import { FormLayout } from "@hilla/react-components/FormLayout.js";

interface PerfilFormProps {
    Perfil?: PerfilRecord | null;
    onSubmit?: (Perfil: PerfilRecord) => Promise<void>;
}

export default function RegisterForm({ Perfil, onSubmit }: PerfilFormProps) {

    const [Perfils, setPerfils] = useState<SelectItem[]>([]);

    const { field, model, submit, reset, read } = useForm(PerfilRecordModel, { onSubmit });
    const eliminarPerfil = async () => {
        if (Perfil && Perfil.id) {
            try {
                // Llamar al servicio para eliminar el registro
                await PerfilService.delete(Perfil.id);

                // Si la eliminación es exitosa, ejecutar la función onSubmit para actualizar el estado de la lista de Perfiles
                if (onSubmit) {
                    await onSubmit(Perfil);
                }

                // Limpiar el formulario después de eliminar el registro
                reset();
            } catch (error) {
                console.error("Error al eliminar el Perfil:", error);
            }
        }
    };

    useEffect(() => {
        read(Perfil);
    }, [Perfil]);

    //control de vacíos del lado del cliente

    const nombre = useFormPart(model.nombre);



    useEffect(() => {
        nombre.addValidator(
            new NotEmpty({
                message: 'Por favor, ingrese un nombre'
            }));

    }, []);

    const responsiveSteps = [
        { minWidth: '0', columns: 1 },
        { minWidth: '500px', columns: 2 },
      ];
    return (
        <>



<Card>
          <CardBody>
            <Heading mb={2} size='sm'>BIENVENIDO A TUPAC</Heading>
            <Divider />
        
            <FormLayout responsiveSteps={responsiveSteps}>
                <TextField label="Nombre" {...field(model.nombre)} />
                <TextField label="Apellido" {...field(model.apellido)} />
                <TextField label="Legajo" {...field(model.legajo)} />
                <TextField label="Password" {...field(model.password)} />     
            </FormLayout>    
            
<Divider/>
            <div className="flex gap-m"  >                
                <Button onClick={submit} theme="secondary small"> 
                    
                    Guardar</Button>

            </div>  
            </CardBody>
            </Card>
        </>
    );

}