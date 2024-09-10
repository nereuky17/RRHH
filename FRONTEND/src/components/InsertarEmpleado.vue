<template>
  <div class="container">
    <div v-if="toastVisible" class="toast">{{ toastMessage }}</div>

    <div class="form-container">
      <h1>Insertar Nuevo Empleado</h1>

      <form @submit.prevent="validarYCrearEmpleado" class="form">
        <div class="form-group">
          <label for="empresa">Empresa:</label>
          <select v-model="nuevoEmpleado.idEmpresa" required>
            <option value="" disabled selected>Seleccione una empresa</option>
            <option v-for="empresa in empresas" :key="empresa.id" :value="empresa.id">
              {{ empresa.nombre }}
            </option>
          </select>
        </div>

        <div class="form-group">
          <label for="nombre">Nombre:</label>
          <input type="text" v-model="nuevoEmpleado.nombre" required />
        </div>

        <div class="form-group">
          <label for="apellido">Apellido:</label>
          <input type="text" v-model="nuevoEmpleado.apellido" />
        </div>

        <div class="form-group">
          <label for="dni">DNI:</label>
          <input type="text" v-model="nuevoEmpleado.dni" required />
        </div>

        <div class="form-group">
          <label for="email">Email:</label>
          <input type="email" v-model="nuevoEmpleado.email" required />
        </div>

        <div class="form-group">
          <label for="telefono">Teléfono:</label>
          <input type="text" v-model="nuevoEmpleado.telefono" required />
        </div>

        <div class="form-group">
          <label for="posicion">Posición:</label>
          <input type="text" v-model="nuevoEmpleado.posicion" required />
        </div>

        <div class="form-group">
          <label for="fechaContratacion">Fecha de Contratación:</label>
          <input type="date" v-model="nuevoEmpleado.fechaContratacion" required />
        </div>

        <button type="submit" class="btn">Agregar Empleado</button>
      </form>
    </div>

    <!-- Modal para mostrar errores -->
    <div v-if="modalVisible" class="modal" @click.self="closeModal">
      <div class="modal-content">
        <span class="close" @click="closeModal">&times;</span>
        <h2>Error en el formulario</h2>
        <ul>
          <li v-for="(error, index) in errores" :key="index">{{ error }}</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { ref } from 'vue';

export default {
  setup() {
    const empresas = ref([]);
    const nuevoEmpleado = ref({
      idEmpresa: '',
      nombre: '',
      apellido: '',
      dni: '',
      email: '',
      telefono: '',
      posicion: '',
      fechaContratacion: ''
    });

    const toastVisible = ref(false);
    const toastMessage = ref('');
    const modalVisible = ref(false);
    const errores = ref([]);

    // Método para obtener empresas del backend
    const fetchEmpresas = async () => {
      try {
        const response = await axios.get('/api/empresas');
        empresas.value = response.data;
      } catch (error) {
        console.error('Error al obtener empresas:', error);
      }
    };

    // Método de validación manual
    const validarYCrearEmpleado = async () => {
      errores.value = []; // Limpiar errores previos

      // Validaciones manuales
      if (!nuevoEmpleado.value.idEmpresa) {
        errores.value.push('La empresa es obligatoria');
      }
      if (nuevoEmpleado.value.nombre.length < 2 || nuevoEmpleado.value.nombre.length > 50) {
        errores.value.push('El nombre debe tener entre 2 y 50 caracteres');
      }
      if (!/^[0-9]{8}[A-Z]$/.test(nuevoEmpleado.value.dni)) {
        errores.value.push('El DNI debe tener 8 números seguidos de una letra');
      }
      if (nuevoEmpleado.value.email && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(nuevoEmpleado.value.email)) {
        errores.value.push('El email debe tener un formato válido');
      }
      if (!/^[0-9]{8,15}$/.test(nuevoEmpleado.value.telefono)) {
        errores.value.push('El teléfono debe ser un número válido con un máximo de 15 dígitos');
      }
      if (nuevoEmpleado.value.posicion.length > 50) {
        errores.value.push('La posición no puede exceder los 50 caracteres');
      }

      // Si hay errores, mostramos el modal
      if (errores.value.length > 0) {
        modalVisible.value = true;
        return;
      }

      // Si no hay errores, intentamos crear el empleado
      try {
        await axios.post(`/api/empleados/empresa/${nuevoEmpleado.value.idEmpresa}`, nuevoEmpleado.value);
        toastMessage.value = 'Empleado agregado correctamente';
        toastVisible.value = true;
        setTimeout(() => {
          toastVisible.value = false;
        }, 3000);
      } catch (error) {
        // Error del backend
        errores.value = ['Error desconocido al crear empleado'];
        modalVisible.value = true;
      }
    };

    const closeModal = () => {
      modalVisible.value = false;
    };

    fetchEmpresas();

    return {
      empresas,
      nuevoEmpleado,
      validarYCrearEmpleado,
      toastVisible,
      toastMessage,
      modalVisible,
      errores,
      closeModal
    };
  }
};
</script>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
}

.form-container {
  background-color: #f5f5f5;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 60%;
  max-width: 700px;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.form-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 1rem;
}

label {
  font-weight: bold;
  margin-bottom: 0.5rem;
}

input, select {
  padding: 0.75rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 1rem;
  width: 100%;
}

.btn {
  background-color: #42b983;
  color: white;
  padding: 0.75rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1.1rem;
  width: 100%;
}

.btn:hover {
  background-color: #369b74;
}

/* Toast styles */
.toast {
  background-color: #42b983;
  color: white;
  padding: 10px;
  position: fixed;
  bottom: 20px;
  right: 20px;
  border-radius: 5px;
  z-index: 100;
}

/* Modal styles */
.modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 5px;
  text-align: center;
  width: 400px;
}

.close {
  position: absolute;
  top: 10px;
  right: 20px;
  font-size: 24px;
  cursor: pointer;
}

.close:hover {
  color: red;
}
</style>