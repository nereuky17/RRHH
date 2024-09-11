<template>
  <div class="container">
    <div v-if="toastVisible" class="toast">{{ toastMessage }}</div>

    <div>
      <h1 class="title">Gestión de Empleados</h1>

      <!-- Filtros -->
      <div class="filters-container">
        <input type="text" v-model="search" placeholder="Filtrar por nombre" @input="filterByName" />
        <button @click="sortAlphabetically">Ordenar Alfabéticamente</button>
        <button @click="resetFilters">Resetear Filtros</button>
      </div>

      <div class="button-container">
        <button class="create-button" @click="abrirModalCrearEmpleado">Insertar Empleado</button>
      </div>

      <!-- Tabla de empleados -->
      <div class="table-container">
        <table>
          <thead>
            <tr>
              <th>Nombre</th>
              <th>Apellido</th>
              <th>DNI</th>
              <th>Email</th>
              <th>Teléfono</th>
              <th>Posición</th>
              <th>Fecha de Contratación</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="empleado in filteredEmpleados" :key="empleado.id">
              <td>{{ empleado.nombre }}</td>
              <td>{{ empleado.apellido }}</td>
              <td>{{ empleado.dni }}</td>
              <td>{{ empleado.email }}</td>
              <td>{{ empleado.telefono }}</td>
              <td>{{ empleado.posicion }}</td>
              <td>{{ empleado.fechaContratacion | formatFecha }}</td>
              <td>
                <button class="btn-details" @click="abrirModalDetalles(empleado)">Ver Detalles</button>
                <button class="btn-edit" @click="abrirModalEditar(empleado)">Editar</button>
                <button class="btn-delete" @click="confirmarBorrarEmpleado(empleado)">Borrar</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- Modal para ver detalles del empleado -->
      <div v-if="modalDetallesVisible" class="modal" @click.self="cerrarModalDetalles">
        <div class="modal-content">
          <span class="close" @click="cerrarModalDetalles">&times;</span>
          <h2>Detalles del Empleado</h2>
          <p><strong>Nombre:</strong> {{ empleadoSeleccionado.nombre }}</p>
          <p><strong>Apellido:</strong> {{ empleadoSeleccionado.apellido }}</p>
          <p><strong>DNI:</strong> {{ empleadoSeleccionado.dni }}</p>
          <p><strong>Email:</strong> {{ empleadoSeleccionado.email }}</p>
          <p><strong>Teléfono:</strong> {{ empleadoSeleccionado.telefono }}</p>
          <p><strong>Posición:</strong> {{ empleadoSeleccionado.posicion }}</p>
        </div>
      </div>

      <!-- Modal para editar empleado -->
      <div v-if="modalEditarVisible" class="modal" @click.self="cerrarModalEditar">
        <div class="modal-content">
          <span class="close" @click="cerrarModalEditar">&times;</span>
          <h2>Editar Empleado</h2>
          <form @submit.prevent="guardarCambiosEmpleado">
            <div class="form-group">
              <label for="nombre">Nombre:</label>
              <input type="text" v-model="empleadoSeleccionado.nombre" required />
            </div>
            <div class="form-group">
              <label for="apellido">Apellido:</label>
              <input type="text" v-model="empleadoSeleccionado.apellido" />
            </div>
            <div class="form-group">
              <label for="dni">DNI:</label>
              <input type="text" v-model="empleadoSeleccionado.dni" required />
            </div>
            <div class="form-group">
              <label for="email">Email:</label>
              <input type="email" v-model="empleadoSeleccionado.email" required />
            </div>
            <div class="form-group">
              <label for="telefono">Teléfono:</label>
              <input type="text" v-model="empleadoSeleccionado.telefono" required />
            </div>
            <div class="form-group">
              <label for="posicion">Posición:</label>
              <input type="text" v-model="empleadoSeleccionado.posicion" required />
            </div>
            <button type="submit">Guardar cambios</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { ref } from 'vue';
import moment from 'moment';

export default {
  setup() {
    const empresas = ref([]);
    const empleados = ref([]);
    const filteredEmpleados = ref([]);
    const search = ref('');
    const toastVisible = ref(false);
    const toastMessage = ref('');
    const modalCrearEmpleadoVisible = ref(false);
    const modalDetallesVisible = ref(false);
    const modalEditarVisible = ref(false);
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
    const empleadoSeleccionado = ref(null);

    const fetchEmpresas = async () => {
      try {
        const response = await axios.get('/api/empresas');
        empresas.value = response.data;
      } catch (error) {
        console.error('Error al obtener empresas:', error);
      }
    };

    const fetchEmpleados = async () => {
      try {
        const response = await axios.get('/api/empleados');
        empleados.value = response.data;
        filteredEmpleados.value = [...empleados.value];
      } catch (error) {
        console.error('Error al obtener empleados:', error);
      }
    };

    const abrirModalDetalles = empleado => {
      empleadoSeleccionado.value = empleado;
      modalDetallesVisible.value = true;
    };

    const cerrarModalDetalles = () => {
      modalDetallesVisible.value = false;
    };

    const abrirModalEditar = empleado => {
      empleadoSeleccionado.value = empleado;
      modalEditarVisible.value = true;
    };

    const cerrarModalEditar = () => {
      modalEditarVisible.value = false;
    };

    const guardarCambiosEmpleado = async () => {
      try {
        await axios.put(`/api/empleados/${empleadoSeleccionado.value.id}`, empleadoSeleccionado.value);
        toastMessage.value = 'Empleado actualizado correctamente';
        toastVisible.value = true;
        modalEditarVisible.value = false;
        fetchEmpleados();
      } catch (error) {
        console.error('Error al actualizar el empleado:', error);
      }
    };

    const confirmarBorrarEmpleado = empleado => {
      if (confirm(`¿Estás seguro de que deseas eliminar a ${empleado.nombre}?`)) {
        borrarEmpleado(empleado.id);
      }
    };

    const borrarEmpleado = async id => {
      try {
        await axios.delete(`/api/empleados/${id}`);
        toastMessage.value = 'Empleado eliminado correctamente';
        toastVisible.value = true;
        fetchEmpleados();
      } catch (error) {
        console.error('Error al eliminar el empleado:', error);
      }
    };

    const formatFecha = fecha => {
      return moment(fecha, 'YYYY-MM-DD').format('DD/MM/YYYY');
    };

    fetchEmpresas();
    fetchEmpleados();

    return {
      empresas,
      empleados,
      filteredEmpleados,
      search,
      toastVisible,
      toastMessage,
      modalCrearEmpleadoVisible,
      modalDetallesVisible,
      modalEditarVisible,
      nuevoEmpleado,
      empleadoSeleccionado,
      abrirModalDetalles,
      cerrarModalDetalles,
      abrirModalEditar,
      cerrarModalEditar,
      guardarCambiosEmpleado,
      confirmarBorrarEmpleado,
      formatFecha
    };
  }
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px;
}

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
  width: 400px;
  text-align: center;
}

.close {
  position: absolute;
  top: 10px;
  right: 20px;
  font-size: 24px;
  cursor: pointer;
}

button {
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #369b74;
}

.btn-details {
  background-color: #add8e6;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-bottom: 5px;
  padding: 10px;
}

.btn-edit {
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-bottom: 5px;
  padding: 10px;
}

.btn-delete {
  background-color: #ff6347;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  padding: 10px;
}

.btn-details:hover, .btn-edit:hover, .btn-delete:hover {
  opacity: 0.8;
}
</style>