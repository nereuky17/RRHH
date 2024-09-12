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
          <RouterLink to="/listado">Empresas</RouterLink>
          <RouterLink to="/insertar">Empleados</RouterLink>
        </nav>
      </div>
    </header>

    <!-- Mostrar el fondo y el contenido de bienvenida solo en la ruta "/" -->
    <div v-if="route.path === '/'" class="welcome-background">
      <div class="overlay">
        <h1 class="titulo">Bienvenido a la Gestoría de empresas</h1>
        <p class="descripcion">
          En nuestra gestoría, ofrecemos soluciones profesionales para la gestión y administración de su empresa. Nuestro equipo de expertos está preparado para ayudarle en todas las áreas clave de su negocio, desde la contabilidad hasta los recursos humanos. Confíe en nosotros para llevar su empresa al siguiente nivel.
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

/* Estilo para el fondo de bienvenida que solo aparece en "/" */
.welcome-background {
  background-image: url('@/assets/fondo.png');
  background-size: 170% auto; /* Cambia el tamaño para extender aún más la imagen hacia la izquierda */
  background-position: -10% center; /* Desplaza la imagen más a la izquierda */
  background-repeat: no-repeat;
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.overlay {
  background-color: rgba(255, 255, 255, 0.7);
  padding: 20px;
  border-radius: 10px;
  text-align: center;
  max-width: 800px;
  width: 90%;
}

.titulo {
  font-size: 2.5rem;
  margin-bottom: 1rem;
  color: #333;
}

.descripcion {
  font-size: 1.2rem;
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
