import router from 'Frontend/routes.js';
import { AuthProvider } from 'Frontend/util/auth.js';
import { RouterProvider } from 'react-router-dom';
import { ChakraProvider } from '@chakra-ui/react'

export default function App() {
  return (

    <AuthProvider>
      <ChakraProvider>
        <RouterProvider router={router} />
      </ChakraProvider>
    </AuthProvider>


  );
}
