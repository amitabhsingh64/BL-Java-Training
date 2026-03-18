import React from 'react';
import {Route,Routes} from 'react-router-dom';
import Dashboard from './pages/dashboard/dashboard.jsx';
import Signup from './pages/signIn/SignIn.jsx';
import Login from './pages/signUp/SignUp.jsx';


const ReactRoutes=()=>{
    return(
        <Routes>
            <Route path='/' element={<Dashboard/>}/>
            <Route path='/signup' element={<Signup/>}/>
            <Route path='/login' element={<Login/>}/>
        </Routes>
    )
}

export default ReactRoutes;