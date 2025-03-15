import {BrowserRouter, Route, Routes} from "react-router-dom";
import './styles/tailwind.css'
import Homepage from "./components/Homepage.jsx";
import Layout from "./components/Layout.jsx";

function App() {

  return (
      <BrowserRouter>
          <Routes>
              <Route path='/' element={<Layout/>}>
                  <Route index element={<Homepage/>}/>
                  {/*<Route path="/Login" element={<LoginPage/>}/>*/}
                  {/*<Route path="/SignUp" element={<SignupPage/>}/>*/}
              </Route>
          </Routes>
      </BrowserRouter>
  )
}

export default App
