import {BrowserRouter, Route, Routes} from "react-router-dom";
import './styles/tailwind.css'
import Homepage from "./Pages/Homepage.jsx";
import Layout from "./Pages/Layout.jsx";
import { ThemeProvider, theme as defaultTheme } from "@material-tailwind/react";
import {radioTheme} from "./Themes/radioTheme.js";
import FlightDisplay from "./Pages/FlightDisplay.jsx";
import {checkboxTheme} from "./Themes/checkboxTheme.js";

function App() {
    const customTheme = {
        ...defaultTheme, // Preserve the default theme
        radio: { ...radioTheme.radio },
        checkbox: {...checkboxTheme.checkbox}// Apply only the radio theme
    };

    return (
    <ThemeProvider  value={customTheme}>
      <BrowserRouter>
          <Routes>
              <Route path='/' element={<Layout/>}>
                  <Route index element={<Homepage/>}/>
                  <Route path="/Flights" element={<FlightDisplay/>}/>
                  {/*<Route path="/SignUp" element={<SignupPage/>}/>*/}
              </Route>
          </Routes>
      </BrowserRouter>
    </ThemeProvider>
  )
}

export default App