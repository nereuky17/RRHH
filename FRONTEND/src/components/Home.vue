<template>
  <div class="home">
    <div class="content">
      <img alt="logo" class="logo" src="@/assets/gestoria.png" />
      <h1>Bienvenido a la Gestoría de empresas</h1>
    </div>
  </div>

  <main>
    <RouterView />
  </main>
</template>

<style scoped>
.home {
  display: flex;
  justify-content: center; /* Centrar horizontalmente */
  align-items: center; /* Centrar verticalmente */
  height: 100vh; /* Ocupar toda la altura de la ventana */
  width: 100vw; /* Ocupar todo el ancho de la ventana */
}

.content {
  text-align: center; /* Asegura que el texto esté centrado */
}

.logo {
  height: 7cm;
  width: 9cm;
  margin-bottom: 2rem; /* Espacio entre la imagen y el texto */
}

button {
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #369b74;
}
</style>
