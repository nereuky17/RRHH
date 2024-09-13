<script setup>
import { RouterLink, RouterView, useRoute } from 'vue-router'

const route = useRoute();
</script>

<template>
  <div id="app">
    <header>
      <div class="wrapper">
        <nav>
          <RouterLink to="/">Inicio</RouterLink>
          <RouterLink to="/empresas">Empresas</RouterLink>
          <RouterLink to="/empleados">Empleados</RouterLink>
        </nav>
      </div>
    </header>

    <!-- Mostrar el fondo y el contenido de bienvenida solo en la ruta "/" -->
    <div v-if="route.path === '/'" class="welcome-background">
      <div class="overlay">
        <h1 class="titulo">Bienvenido/a a la Gestoría de empresas</h1>
        <p class="descripcion">
          En nuestra gestoría, ofrecemos soluciones profesionales para la gestión y administración de su empresa. Nuestro equipo de expertos está preparado para ayudarle en todas las áreas clave de su negocio, desde la contabilidad hasta los recursos humanos. Confíe en nosotros para llevar a su empresa al siguiente nivel.
        </p>
      </div>
    </div>

    <!-- Contenido principal que cambiará según las rutas -->
    <div class="content">
      <RouterView />
    </div>
  </div>
</template>

<style scoped>

html, body {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
  overflow-x: hidden; /* Oculta el scroll horizontal */
}

#app {
  width: 100vw;
  height: 100vh;
}

body {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-size: 16px; 
}

header {
  line-height: 1.5;
  display: block;
  position: fixed; 
  top: 0;
  left: 0; 
  right: 0; 
  width: 100%; 
  background-color: #d0e1ff; 
  z-index: 1000;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); 
  padding: 0; 
}

nav a {
  display: inline-block;
  padding: 1em;
  border-left: 1px solid var(--color-border);
  color: var(--color-text);
  text-decoration: none;
  transition: background-color 0.3s ease;
}

nav a.router-link-exact-active {
  background-color: #4a87c4; 
  color: white; 
  font-weight: bold; 
}

nav a.router-link-exact-active:hover {
  background-color: #4a87c4;
}

nav a:first-of-type {
  border: 0;
}

.welcome-background {
  position: relative;
  width: 100vw;
  height: 120vh; /* Aumenta el alto para hacer la imagen más grande hacia abajo */
  display: flex;
  justify-content: center;
  align-items: center;
}

.welcome-background::before {
  content: "";
  position: absolute;
  top: -30%; 
  left: 0;
  width: 150%; 
  height: 135vh; 
  background-image: url('@/assets/fondo.png');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  transform: translateX(-30%); 
  z-index: -1;
}

/* Ajusta el contenedor del texto */
.overlay {
  background-color: rgba(255, 255, 255, 0.85); 
  padding: 40px;
  border-radius: 10px;
  max-width: 90%; 
  text-align: center;
  position: absolute;
  z-index: 1;
}

/* Centrando verticalmente el texto */
.welcome-background .overlay {
  top: 50%;
  left: 50%;
  transform: translate(-80%, -50%);
}

.welcome-background .overlay h1 {
  font-size: 3rem;
  text-align: center;
  text-decoration: underline; 
  margin-bottom: 1rem;
}

.welcome-background .overlay p {
  font-weight: bold; 
  font-size: 1.8rem;
  text-align: justify;
}
.titulo {
  font-size: 2.5rem;
  margin-bottom: 1rem;
  color: #333;
}

.descripcion {
  font-size: 1.6rem;
  color: #333;
}

/* Ajustes para la sección de contenido */
.content {
  padding-top: 160px; 
}

/* Ajustes para pantallas pequeñas */
@media (max-width: 768px) {
  nav a {
    padding: 0.5em;
    font-size: 0.8em;
  }

  .content {
    padding-top: 120px;
  }

  /* En pantallas pequeñas, aseguramos que la imagen siga visible */
  .welcome-background {
    background-size: cover;
    background-position: center;
  }
}

/* Ajustes para pantallas medianas */
@media (min-width: 769px) and (max-width: 1024px) {
  nav a {
    padding: 0.8em;
    font-size: 0.9em;
  }

  .content {
    padding-top: 140px;
  }
}

/* Ajustes para pantallas grandes */
@media (min-width: 1025px) {
  nav a {
    padding: 1em;
    font-size: 1.1em;
  }

  .content {
    padding-top: 160px; 
  }
}
</style>
