import "bootstrap/dist/css/bootstrap.min.css";

export default function Navbar(){
    return(
        <nav className="navbar bg-primary border-bottom border-body navbar-expand-lg fixed-top">
            <div className="container-fluid justify-content-center d-flex" style={{height: "60px"}}>
                <a className="navbar-brand text-center m-0">KartingRM</a>
            </div>  
        </nav>
    );
}