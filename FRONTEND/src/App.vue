<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
   

    <div class="wrapper">
      <nav>
        <RouterLink to="/">Inicio</RouterLink>
        <RouterLink to="/listado">Empresas</RouterLink>
        <RouterLink to="/insertar">Empleados</RouterLink>
      </nav>
    </div>
  </header>
  <div class="content">
    <RouterView />
  </div>
</template>

<style scoped>

body {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-size: 16px; 
}

header {
  line-height: 1.5;
  max-height: 100vh;
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

.logo {
  display: block;
  margin: 0 auto;
  max-width: 100%; 
  height: auto; 
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

/* Ajustes para pantallas pequeñas (hasta 768px) */
@media (max-width: 768px) {
  nav a {
    padding: 0.5em;
    font-size: 0.8em;
  }

  .logo {
    width: 50px;
    height: auto; 
  }

  .content {
    padding-top: 120px;
  }
}

/* Ajustes para pantallas medianas (769px a 1024px) */
@media (min-width: 769px) and (max-width: 1024px) {
  nav a {
    padding: 0.8em;
    font-size: 0.9em;
  }

  .logo {
    width: 100px;
    height: auto;
  }

  .content {
    padding-top: 140px;
  }
}

/* Ajustes para pantallas grandes (más de 1024px) */
@media (min-width: 1025px) {
  nav a {
    padding: 1em;
    font-size: 1.1em;
  }

  .logo {
    width: 125px;
    height: auto;
  }

  .content {
    padding-top: 160px; 
  }
}
</style>
