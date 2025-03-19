import React from 'react';
import {Outlet} from 'react-router-dom';
import Header from "../components/Header.jsx";
import '../styles/tailwind.css'

function Layout({children}) {
    return (
        <div className="flex flex-col bg-background">
            <Header/>

            <main className="App">
                <Outlet/>
            </main>
        </div>
    )
}

export default Layout;