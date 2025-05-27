import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate } from "react-router-dom";


export default function Home(){
    const navigate = useNavigate("")

    const handleAddClient = () =>{
        navigate("/addClient");
    }

    const handleLoginClient = () => {
        navigate("/loginClient")
    }

    return (
        <div>
            <p>Bienvenido al servicio de KartingRM, ¿Qué desea hacer?</p>

            <div>
                <button type="button" className="btn btn-warning" style={{margin: "20px"}} onClick={handleAddClient}>Registrar Usuario</button>
                <button type="button" className="btn btn-success" onClick={handleLoginClient}>Login de Usuario</button>
            </div>
        </div>
    )
}