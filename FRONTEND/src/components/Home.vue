<template>
  <div class="home">
    <div class="overlay">
      <h1 class="titulo">Bienvenido a la Gestoría de empresas</h1>
      <p class="descripcion">
        En nuestra gestoría, ofrecemos soluciones profesionales para la gestión y administración de su empresa. Nuestro equipo de expertos está preparado para ayudarle en todas las áreas clave de su negocio, desde la contabilidad hasta los recursos humanos. Confíe en nosotros para llevar su empresa al siguiente nivel.
      </p>
    </div>
  </div>
</template>

<style scoped>
html, body {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
  overflow: hidden; /* Elimina cualquier tipo de scroll en la página */
}

.home {

  background-size: cover; /* Asegura que la imagen cubra todo el área */
  background-position: center;
  background-repeat: no-repeat;
  width: 100vw; /* Asegura que ocupe todo el ancho de la pantalla */
  height: 100vh; /* Asegura que ocupe toda la altura de la pantalla */
  display: flex;
  justify-content: center;
  align-items: center;
}

.overlay {
  background-color: rgba(255, 255, 255, 0.7); /* Fondo semitransparente para el texto */
  padding: 20px;
  border-radius: 10px;
  max-width: 800px;
  width: 90%; /* Tamaño responsivo */
  text-align: center;
}



.titulo {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

.descripcion {
  font-size: 1.2rem;
  margin: 0;
}


</style>