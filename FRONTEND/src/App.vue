<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
    <img alt="logo" class="logo" src="@/assets/gestoria.png" width="125" height="125" />

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
header {
  line-height: 1.5;
  max-height: 100vh;
  display: block;
}

.logo {
  display: block;
  margin: 0 auto 0;
}



nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 1em;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    align-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }
}
</style>
