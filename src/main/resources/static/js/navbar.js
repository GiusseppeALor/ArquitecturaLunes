document.addEventListener("DOMContentLoaded", () => {
  const navLinks = document.getElementById("nav-links");
  const usuario = JSON.parse(localStorage.getItem("usuario")); // lo guardaste al hacer login

  // Si el usuario está logueado como cliente
  if (usuario && usuario.rol === "cliente") {
    navLinks.innerHTML = `
      <li><a href="/index.html">Inicio</a></li>
      <li><a href="/catalogo.html">Catálogo</a></li>
      <li><a href="/reserva.html">Reservas</a></li>
      <li><a href="/perfil.html">Perfil</a></li>
      <li><a href="#" id="cerrarSesion">Cerrar sesión</a></li>
    `;

    // Acción del botón cerrar sesión
    document.getElementById("cerrarSesion").addEventListener("click", (e) => {
      e.preventDefault();
      localStorage.removeItem("usuario");
      alert("Sesión cerrada correctamente.");
      window.location.href = "/login.html";
    });

  } else {
    navLinks.innerHTML = `
      <li><a href="/index.html">Inicio</a></li>
      <li><a href="/catalogo.html">Catálogo</a></li>
      <li><a href="/login.html">Iniciar sesión</a></li>
    `;
  }
});
