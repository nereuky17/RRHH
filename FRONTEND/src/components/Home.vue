<template>
  <div class="home">
    <div class="content">
      <img alt="logo" class="logo" src="@/assets/gestoria.png" />
      <h1 class="titulo">Bienvenido a la Gestoría de empresas</h1>
    </div>
  </div>

  <main>
    <RouterView />
  </main>
</template>

<style scoped>
.home {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh; 
  width: 100vw;
  margin: 0;
  padding: 0; 
}

.content {
  text-align: center; 
}

.logo {
  display: block;
  margin: auto;
 
  margin-left: -20%;
  height: auto;
  max-width: 60%;
  margin-bottom: 2rem; 
}
.titulo{
  margin-left: -60%;
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
