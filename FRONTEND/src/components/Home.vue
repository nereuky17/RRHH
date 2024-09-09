<template>
    <div class="home">
      <img alt="logo" class="logo" src="@/assets/gestoria.png"/>
      <h1>Bienvenido a la Gestoría de empresas</h1>
     
    </div>


  <main>

<RouterView />
</main>
  </template>
  
  <style scoped>

  nav {
    display: flex;
    justify-content:center ;
    gap: 2rem;
    margin-top: 0%;
  }
  
  .logo {
    height: 7cm;
    width: 9cm;
    margin: 2 6rem 0 0;
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
  