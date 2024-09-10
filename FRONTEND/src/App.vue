<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
   

    <div class="wrapper">
      <nav>
        <RouterLink to="/">Página de inicio</RouterLink>
        <RouterLink to="/listado">Empresas</RouterLink>
        <RouterLink to="/insertar">Empleado</RouterLink>
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
  background-color: #000080; 
  color: white; 
  font-weight: bold; 
}

nav a.router-link-exact-active:hover {
  background-color: #000066;
}

nav a:first-of-type {
  border: 0;
}

@media (max-width: 768px) {
  nav a {
    padding: 0.5em;
    font-size: 0.9em;
  }

  .logo {
    width: 80px;
    height: 80px;
  }

  .content {
    padding-top: 120px;
  }
}

@media (min-width: 769px) and (max-width: 1024px) {
  nav a {
    padding: 0.8em;
    font-size: 1em;
  }

  .logo {
    width: 100px;
    height: 100px;
  }

  .content {
    padding-top: 140px;
  }
}


@media (min-width: 1025px) {
  nav a {
    padding: 1em;
    font-size: 1.1em;
  }

  .logo {
    width: 125px;
    height: 125px;
  }
}
</style>
