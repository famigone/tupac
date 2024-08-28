import HelloWorldView from 'Frontend/views/helloworld/HelloWorldView.js';
import MainLayout from 'Frontend/views/MainLayout.js';
import { lazy } from 'react';
import { createBrowserRouter, RouteObject } from 'react-router-dom';
import HomeMateria from './views/materia/HomeMateria';
import LoginView from './views/login/LoginView';
import RegisterForm from './views/login/RegisterForm';
import HomePractico from './views/practico/HomePractico';
import HomeDesafio from './views/desafio/HomeDesafio';
import HomePerfil from './views/perfil/HomePerfil';
const AboutView = lazy(async () => import('Frontend/views/about/AboutView.js'));

const routing = [
  {
    element: <MainLayout />,
    handle: { title: 'Main' },
    children: [
      { path: '/', element: <HelloWorldView />, handle: { title: 'Hello World' } },
      { path: '/about', element: <AboutView />, handle: { title: 'About' } },
      { path: '/materia', element: <HomeMateria />, handle: { title: 'Materias' } },
      { path: '/estudiante', element: <HomePerfil />, handle: { title: 'Estudiantes' } },
      { path: '/practico/:materiaid', element: <HomePractico />, handle: { title: 'Practico' } },
      { path: '/desafio/:practicoid', element: <HomeDesafio />, handle: { title: 'Desafíos' } },
      
      
    ],
  },
  { path: '/register', element: <RegisterForm />},
  { path: '/login', element: <LoginView /> },
] as RouteObject[];

export const routes = routing;
export default createBrowserRouter(routes);
