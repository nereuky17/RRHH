<template>
  <div class="container">
    <div v-if="toastVisible" class="toast">{{ toastMessage }}</div>

    <div>
      <h1 class="title">Gestión de Empleados</h1>

      <!-- Filtros -->
      <div class="filters-container">
        <input type="text" v-model="search" placeholder="Filtrar por nombre" @input="filterByName" />
        <button class="action-button" @click="sortAlphabetically">Ordenar Alfabéticamente</button>
        <button class="action-button" @click="resetFilters">Resetear Filtros</button>
      </div>

      <div class="button-container">
        <button class="action-button" @click="abrirModalCrearEmpleado">Insertar Empleado</button>
      </div>

      <!-- Tabla de empleados -->
      <div class="table-container">
        <div class="scrollable-table">
        <table>
          <thead>
            <tr>
              <th>Nombre</th>
              <th>Apellido</th>
              <th>DNI</th>
              <th>Email</th>
              <th>Teléfono</th>
              <th>Posición</th>
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
         


              <td>
                <button class="action-button" @click="abrirModalDetalles(empleado)">Ver</button>
                <button class="action-button" @click="abrirModalEditar(empleado)">Editar</button>
                <button class="action-button delete-button" @click="abrirModalBorrar(empleado)">Borrar</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

      <!-- Modal para borrar empleado -->
      <div v-if="modalBorrarVisible" class="modal" @click.self="cerrarModalBorrar">
        <div class="modal-content">
          <span class="close" @click="cerrarModalBorrar">&times;</span>
          <h2>¿Estás seguro de que deseas eliminar a {{ empleadoSeleccionado.nombre }}?</h2>
          <div class="modal-buttons">
            <button class="btn-confirm" @click="borrarEmpleado">Sí, eliminar</button>
            <button class="btn-cancel" @click="cerrarModalBorrar">Cancelar</button>
          </div>
        </div>
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
      <div v-if="modalEditarVisible" class="modal">
    <div class="modal-content">
      <span class="close" @click="cerrarModalEditar">&times;</span>
      <h2>Editar Empleado</h2>
      <form @submit.prevent="guardarCambiosEmpleado">
        <div class="form-group">
          <label for="nombre">Nombre:</label>
          <input type="text" v-model="empleadoEditado.nombre" required />
        </div>
        <div class="form-group">
          <label for="apellido">Apellido:</label>
          <input type="text" v-model="empleadoEditado.apellido" />
        </div>
        <div class="form-group">
          <label for="dni">DNI:</label>
          <input type="text" v-model="empleadoEditado.dni" required />
        </div>
        <div class="form-group">
          <label for="email">Email:</label>
          <input type="email" v-model="empleadoEditado.email" required />
        </div>
        <div class="form-group">
          <label for="telefono">Teléfono:</label>
          <input type="text" v-model="empleadoEditado.telefono" required />
        </div>
        <div class="form-group">
          <label for="posicion">Posición:</label>
          <input type="text" v-model="empleadoEditado.posicion" required />
        </div>
        <div class="form-group button-group">
          <button type="submit">Guardar cambios</button>
          <button type="button" @click="cerrarModalEditar">Cancelar</button>
        </div>
      </form>
  </div>
</div>



      <!-- Modal para crear empleado -->
      <div v-if="modalCrearEmpleadoVisible" class="modal" @click.self="cerrarModalCrearEmpleado">
        <div class="modal-content">
          <span class="close" @click="cerrarModalCrearEmpleado">&times;</span>
          <h2>Crear Empleado</h2>
          <form @submit.prevent="crearEmpleado">
            <div class="form-group">
              <label for="nombre">Nombre:</label>
              <input type="text" v-model="nuevoEmpleado.nombre"
                title="El nombre es obligatorio y debe tener entre 2 y 50 caracteres" required />
            </div>
            <div class="form-group">
              <label for="apellido">Apellido:</label>
              <input type="text" v-model="nuevoEmpleado.apellido"
                title="El apellido no puede exceder los 50 caracteres" />
            </div>
            <div class="form-group">
              <label for="dni">DNI:</label>
              <input type="text" v-model="nuevoEmpleado.dni"
                title="El DNI es obligatorio y debe tener 8 números seguidos de una letra" required />
            </div>
            <div class="form-group">
              <label for="email">Email:</label>
              <input type="email" v-model="nuevoEmpleado.email"
                title="El email debe tener un formato válido y no exceder los 255 caracteres" required />
            </div>
            <div class="form-group">
              <label for="telefono">Teléfono:</label>
              <input type="text" v-model="nuevoEmpleado.telefono"
                title="El teléfono debe ser un número válido con un máximo de 15 dígitos" required />
            </div>
            <div class="form-group">
              <label for="posicion">Posición:</label>
              <input type="text" v-model="nuevoEmpleado.posicion" title="La posición no puede exceder los 50 caracteres"
                required />
            </div>

         <!-- Combo para seleccionar la empresa -->
      <div class="form-group">
        <label for="empresa">Empresa:</label>
        <select v-model="empresaSeleccionada" class="wide-select">
          <option v-for="empresa in empresas" :key="empresa.id" :value="empresa.id">
            {{ empresa.nombre }}
          </option>
        </select>
      </div>

      <div class="button-group">
        <button class="action-button" type="submit">Guardar</button>
        <button class="action-button delete-button" @click="cerrarModalCrearEmpleado">Cancelar</button>
      </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { ref } from 'vue';
import { useToast } from 'vue-toastification';
export default {
  setup() {
    const toast = useToast();
    const empresas = ref([]);
    const empleados = ref([]);
    const filteredEmpleados = ref([]);
    const search = ref('');
    const toastVisible = ref(false);
    const toastMessage = ref('');
    const modalCrearEmpleadoVisible = ref(false);
    const modalDetallesVisible = ref(false);
    const modalEditarVisible = ref(false);
    const modalBorrarVisible = ref(false);
    const empresaSeleccionada = ref(null);

    const nuevoEmpleado = ref({
      idEmpresa: '',
      nombre: '',
      apellido: '',
      dni: '',
      email: '',
      telefono: '',
      posicion: ''
    });

    const empleadoSeleccionado = ref(null);
    const empleadoEditado = ref(null);  

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

    // Función para abrir el modal de edición
    const abrirModalEditar = (empleado) => {
      empleadoSeleccionado.value = empleado;  
      empleadoEditado.value = { ...empleado };  
      modalEditarVisible.value = true;
    };

    // Función para cerrar el modal de edición sin guardar cambios
    const cerrarModalEditar = () => {
      empleadoEditado.value = null;  
      modalEditarVisible.value = false;
    };

    // Función para guardar los cambios realizados en el empleado
    const guardarCambiosEmpleado = async () => {
      try {
        await axios.put(`/api/empleados/${empleadoEditado.value.id}`, empleadoEditado.value);
        
        Object.assign(empleadoSeleccionado.value, empleadoEditado.value);

        toast.success('Empleado actualizado correctamente');
        modalEditarVisible.value = false;

        fetchEmpleados();  // Actualizamos la lista de empleados
      } catch (error) {
        console.error('Error al actualizar el empleado:', error);
        toast.error('Error al actualizar el empleado');
      }
    };

    const abrirModalCrearEmpleado = () => {
      // Resetear los campos antes de abrir el modal
      nuevoEmpleado.value = {
        nombre: '',
        apellido: '',
        dni: '',
        email: '',
        telefono: '',
        posicion: ''
      };
      empresaSeleccionada.value = null;

      modalCrearEmpleadoVisible.value = true;
    };

    const cerrarModalCrearEmpleado = () => {
      modalCrearEmpleadoVisible.value = false;
    };

    const crearEmpleado = async () => {
      if (!empresaSeleccionada.value) {
        console.error('Debe seleccionar una empresa');
        return;
      }

      try {
        const empleadoData = { ...nuevoEmpleado.value };
        await axios.post(`/api/empleados/empresa/${empresaSeleccionada.value}`, empleadoData);
        toastMessage.value = 'Empleado creado correctamente';
        toastVisible.value = true;

        setTimeout(() => {
          toastVisible.value = false;
        }, 3000);

        // Resetear campos del formulario
        nuevoEmpleado.value = {
          nombre: '',
          apellido: '',
          dni: '',
          email: '',
          telefono: '',
          posicion: ''
        };
        empresaSeleccionada.value = null;

        modalCrearEmpleadoVisible.value = false;
        fetchEmpleados();
      } catch (error) {
        if (error.response && error.response.status === 409) {
          toast.error('El DNI ya existe');
        } else {
          toast.error('Error al crear el empleado');
        }
        console.error('Error al crear el empleado:', error);
      }
    };

    const abrirModalDetalles = empleado => {
      empleadoSeleccionado.value = empleado;
      modalDetallesVisible.value = true;
    };

    const cerrarModalDetalles = () => {
      modalDetallesVisible.value = false;
    };

    const abrirModalBorrar = empleado => {
      empleadoSeleccionado.value = empleado;
      modalBorrarVisible.value = true;
    };

    const cerrarModalBorrar = () => {
      modalBorrarVisible.value = false;
    };

    const borrarEmpleado = async () => {
      try {
        await axios.delete(`/api/empleados/${empleadoSeleccionado.value.id}`);
        toastMessage.value = 'Empleado eliminado correctamente';
        toastVisible.value = true;
        modalBorrarVisible.value = false;
        setTimeout(() => {
          toastVisible.value = false;
        }, 3000);
        fetchEmpleados();
      } catch (error) {
        console.error('Error al eliminar el empleado:', error);
      }
    };

    const filterByName = () => {
      const searchLower = search.value.toLowerCase();
      filteredEmpleados.value = empleados.value.filter(empleado =>
        empleado.nombre.toLowerCase().startsWith(searchLower)
      );
    };

    const sortAlphabetically = () => {
      filteredEmpleados.value.sort((a, b) =>
        a.nombre.localeCompare(b.nombre)
      );
    };

    const resetFilters = () => {
      search.value = '';
      filteredEmpleados.value = [...empleados.value];
    };

    fetchEmpleados();
    fetchEmpresas();

    return {
      empresas,
      empleados,
      empleadoSeleccionado,
      empleadoEditado,  
      filteredEmpleados,
      search,
      toastVisible,
      toastMessage,
      modalCrearEmpleadoVisible,
      modalDetallesVisible,
      modalEditarVisible,
      modalBorrarVisible,
      nuevoEmpleado,
      empresaSeleccionada,
      abrirModalCrearEmpleado,
      cerrarModalCrearEmpleado,
      crearEmpleado,
      abrirModalDetalles,
      cerrarModalDetalles,
      abrirModalEditar,
      cerrarModalEditar,
      guardarCambiosEmpleado,
      abrirModalBorrar,
      cerrarModalBorrar,
      borrarEmpleado,
      filterByName,
      sortAlphabetically,
      resetFilters,
    };
  }
};
</script>

<style scoped>

.butt{
margin-top: 20px; 
padding: 10px 20px;
}
.cancel-button {
  background-color: #aaa;
  margin-left: 10px;
  padding: 10px;
  color: white;
  border-radius: 4px;
  cursor: pointer;
}

.cancel-button:hover {
  background-color: #888;
}
.btn-confirm {
  background-color: #f44336;
  color: white;
  padding: 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-confirm:hover {
  background-color: #d32f2f;
}

.scrollable-table {
  max-height: 600px; 
  overflow-y: auto; 
}

.title {
  text-align: center;
  color: #5c99d6;
  text-decoration: underline;
  margin-bottom: 20px;
}

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
  border-radius: 8px;
  width: 600px;  
  max-width: 90%;
  min-height: 200px;  
  text-align: center;
  border: 2px solid #5c99d6;
  display: flex;
  flex-direction: column;
  justify-content: space-between;  
}

.form-group {

  margin-bottom: 20px; 
}

.action-button {
  margin-top: 20px; 
  padding: 10px 20px;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 4px;
}


.modal-title {
  color: #5c99d6;
  margin-bottom: 20px;
}

.modal-buttons {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}

.btn-delete {
  background-color: #f44336;
  color: white;
  padding: 10px;
  border-radius: 4px;
  cursor: pointer;
}

.btn-delete:hover {
  background-color: #d32f2f;
}

.btn-cancel {
  background-color: #aaa;
  color: white;
  padding: 10px;
  border-radius: 4px;
  cursor: pointer;
}

.btn-cancel:hover {
  background-color: #888;
}

.form-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: stretch;
  gap: 10px;
  border: 1px solid #ccc;
}


.form-group label {
  margin-bottom: 5px;
  font-weight: bold;
  
}

.form-group input {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  width: 100%;
  box-sizing: border-box;
}

.form-group input,
.form-group select {
  background-color: #f0f0f0; 
  border: 1px solid #ccc;
  padding: 8px;
  border-radius: 4px;
  width: 100%;
  box-sizing: border-box;
}

.form-group input:focus,
.form-group select:focus {
  outline: none;
  border-color: #5c99d6; 
  background-color: #e0e0e0; 
}

.filters-container button {
  margin-right: 10px; 
}

.btn-submit {
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  width: 100%;
}

.btn-submit:hover {
  background-color: #369b74;
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

.delete-button {
  background-color: #f44336;
}

.delete-button:hover {
  background-color: #d32f2f;
}

th,
td {
  border: 1px solid #5c99d6;
  padding: 8px;
  text-align: left;
}

th {
  background-color: #5c99d6;
  color: white;
}

tbody tr:nth-child(even) {
  background-color: #f2f9ff;
}

tbody tr:nth-child(odd) {
  background-color: #ffffff;
}

td {
  color: #000000;
}

.toast {
  position: fixed;
  bottom: 20px;
  right: 20px;
  background-color: #5cb85c;
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  z-index: 9999;
  display: inline-block;
}

.wide-select {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

</style>
