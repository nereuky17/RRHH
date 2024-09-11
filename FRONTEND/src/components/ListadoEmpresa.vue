<template>
  <div v-if="toastVisible" class="toast">
    {{ toastMessage }}
  </div>
  <div>
    <h1 class="title"><br>Gestión de Empresas<br></h1>

    <!-- Filtros -->
    <div class="filters-container">
      <input type="text" v-model="search" placeholder="Filtrar por nombre" @input="filterByName" />
      <button class="action-button" @click="sortAlphabetically">Ordenar Alfabéticamente</button>
      <button class="action-button" @click="resetFilters">Resetear Filtros</button>
    </div>

    <div class="button-container">
      <button class="action-button" @click="abrirModalCrearEmpresa">Crear </button>
    </div>

    <!-- Tabla de empresas -->
    <div class="table-container">
      <table>
        <thead>
          <tr>
           
            <th>Nombre</th>
            <th>CIF</th>
            <th>Dirección</th>
            <th>Teléfono</th>
            <th>Email</th>
            <th>Página Web</th>
            <th>Sector</th>
            <th>País</th>
            <th>Empleados</th>
            <th>Editar</th>
            <th>Borrar</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="empresa in filteredEmpresas" :key="empresa.id">
           
            <td>{{ empresa.nombre }}</td>
            <td>{{ empresa.cif }}</td>
            <td>{{ empresa.direccion }}</td>
            <td>{{ empresa.telefono }}</td>
            <td>{{ empresa.email }}</td>
            <td>{{ empresa.paginaWeb }}</td>
            <td>{{ empresa.sector }}</td>
            <td>{{ empresa.pais }}</td>
            <td>
  <button class="action-button" @click="mostrarEmpleados(empresa)">Ver Empleados</button>
</td>
<td>
  <button class="action-button" @click="abrirModalEditarEmpresa(empresa)">Editar Empresa</button>
</td>
<td>
  <button class="action-button delete-button" @click="confirmarBorrarEmpresa(empresa)">Borrar Empresa</button>
</td>
          </tr>
        </tbody>
      </table>
    </div>

<!-- Modal de confirmación para borrar empresa -->
<div v-if="modalBorrarEmpresaVisible" class="modal">
  <div class="modal-content">
    <span class="close" @click="cerrarModalBorrarEmpresa">&times;</span>
    <h2>¿Estás seguro de que deseas eliminar la empresa {{ empresaSeleccionada.nombre }}?</h2>
    <button class="action-button" @click="borrarEmpresa">Sí, eliminar</button>
    <button class="action-button" @click="cerrarModalBorrarEmpresa">Cancelar</button>
  </div>
</div>

    <!-- Modal para editar empresa -->
    <div v-if="modalEditarEmpresaVisible" class="modal" @click.self="cerrarModalEditarEmpresa">
      <div class="modal-content">
        <span class="close" @click="cerrarModalEditarEmpresa">&times;</span>
        <h2>Editar Empresa</h2>
        <form @submit.prevent="guardarCambiosEmpresa">
          <div class="form-group">
            <label for="nombre">Nombre:</label>
            <input type="text" v-model="empresaSeleccionada.nombre" required />
          </div>
          <div class="form-group">
            <label for="cif">CIF:</label>
            <input type="text" v-model="empresaSeleccionada.cif" required />
          </div>
          <div class="form-group">
            <label for="direccion">Dirección:</label>
            <input type="text" v-model="empresaSeleccionada.direccion" />
          </div>
          <div class="form-group">
            <label for="telefono">Teléfono:</label>
            <input type="text" v-model="empresaSeleccionada.telefono" />
          </div>
          <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" v-model="empresaSeleccionada.email" />
          </div>
          <div class="form-group">
            <label for="paginaWeb">Página Web:</label>
            <input type="text" v-model="empresaSeleccionada.paginaWeb" />
          </div>
          <div class="form-group">
            <label for="sector">Sector:</label>
            <input type="text" v-model="empresaSeleccionada.sector" />
          </div>
          <div class="form-group">
            <label for="pais">País:</label>
            <input type="text" v-model="empresaSeleccionada.pais" />
          </div>
          <div class="form-group button-group">
            <button type="submit">Guardar cambios</button>
            <button type="button" @click="cerrarModalEditarEmpresa">Cancelar</button>
          </div>
        </form>
      </div>
    </div>


    <!-- Modal para mostrar empleados -->
    <div v-if="empleadosVisible" class="empleados-modal">
      <h2>Empleados de {{ empresaSeleccionada.nombre }}</h2>
      <table>
        <thead>
          <tr>
            <th>Nombre</th>
            <th>Detalles</th>
            <th>Editar</th>
            <th>Borrar</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="empleado in empleados" :key="empleado.id">
            <td>{{ empleado.nombre }}</td>
            <td><button @click="verDetalles(empleado)">Ver detalles</button></td>
            <td><button @click="editarEmpleado(empleado)">Editar</button></td>
            <td><button @click="confirmarBorrarEmpleado(empleado)">Borrar</button></td>
          </tr>
        </tbody>
      </table>
      <button @click="cerrarModalEmpleados">Cerrar</button>
    </div>

    <!-- Modal para ver detalles del empleado -->
    <div v-if="modalDetallesVisible" class="modal">
      <div class="modal-content">
        <span class="close" @click="cerrarModalDetalles">&times;</span>
        <h2>Detalles del empleado</h2>
        <p><strong>Nombre:</strong> {{ empleadoSeleccionado.nombre }}</p>
        <p><strong>Posición:</strong> {{ empleadoSeleccionado.posicion }}</p>
        <p><strong>Email:</strong> {{ empleadoSeleccionado.email }}</p>
        <p><strong>Teléfono:</strong> {{ empleadoSeleccionado.telefono }}</p>
        <button @click="cerrarModalDetalles">Cerrar</button>
      </div>
    </div>

    <!-- Modal para editar empleado -->
    <div v-if="modalEditarVisible" class="modal">
      <div class="modal-content">
        <span class="close" @click="cerrarModalEditar">&times;</span>
        <h2>Editar empleado</h2>
        <form @submit.prevent="guardarCambiosEmpleado">
          <div class="form-group">
            <label for="nombre">Nombre:</label>
            <input type="text" v-model="empleadoSeleccionado.nombre" required />
          </div>
          <div class="form-group">
            <label for="posicion">Posición:</label>
            <input type="text" v-model="empleadoSeleccionado.posicion" required />
          </div>
          <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" v-model="empleadoSeleccionado.email" required />
          </div>
          <div class="form-group">
            <label for="telefono">Teléfono:</label>
            <input type="text" v-model="empleadoSeleccionado.telefono" required />
          </div>
          <div class="form-group button-group">
            <button type="submit">Guardar cambios</button>
            <button type="button" @click="cerrarModalEditar">Cancelar</button>
          </div>
        </form>
      </div>
    </div>

    <!-- Modal de confirmación para borrar empleado -->
    <div v-if="modalBorrarVisible" class="modal">
      <div class="modal-content">
        <span class="close" @click="cerrarModalBorrar">&times;</span>
        <h2>¿Estás seguro de que deseas eliminar a {{ empleadoSeleccionado.nombre }}?</h2>
        <button @click="borrarEmpleado">Sí, eliminar</button>
        <button @click="cerrarModalBorrar">Cancelar</button>
      </div>
    </div>

    <!-- Modal para crear empresa -->
    <div v-if="modalCrearEmpresaVisible" class="modal" @click.self="cerrarModalCrearEmpresa">
      <div class="modal-content">
        <span class="close" @click="cerrarModalCrearEmpresa">&times;</span>

        <form @submit.prevent="crearEmpresa">
          <div class="form-group tooltip-container">
            <label for="nombre">Nombre:</label>
            <input type="text" v-model="nuevaEmpresa.nombre" required />
            <span class="tooltip-text">El nombre de la empresa debe tener entre 2 y 100 caracteres</span>
          </div>
          <div class="form-group tooltip-container">
            <label for="cif">CIF:</label>
            <input type="text" v-model="nuevaEmpresa.cif" required />
            <span class="tooltip-text">El CIF debe tener entre 8 y 10 caracteres alfanuméricos</span>
          </div>
          <div class="form-group tooltip-container">
            <label for="direccion">Dirección:</label>
            <input type="text" v-model="nuevaEmpresa.direccion" />
            <span class="tooltip-text">La dirección debe tener entre 5 y 255 caracteres</span>
          </div>
          <div class="form-group tooltip-container">
            <label for="telefono">Teléfono:</label>
            <input type="text" v-model="nuevaEmpresa.telefono" />
            <span class="tooltip-text">El teléfono debe tener entre 8 y 15 dígitos</span>
          </div>
          <div class="form-group tooltip-container">
            <label for="email">Email:</label>
            <input type="email" v-model="nuevaEmpresa.email" />
            <span class="tooltip-text">El email debe ser válido y no exceder los 255 caracteres</span>
          </div>
          <div class="form-group tooltip-container">
            <label for="paginaWeb">Página Web:</label>
            <input type="text" v-model="nuevaEmpresa.paginaWeb" />
            <span class="tooltip-text">La página web no debe exceder los 100 caracteres</span>
          </div>
          <div class="form-group tooltip-container">
            <label for="sector">Sector:</label>
            <input type="text" v-model="nuevaEmpresa.sector" />
            <span class="tooltip-text">El sector debe tener entre 2 y 50 caracteres</span>
          </div>
          <div class="form-group tooltip-container">
            <label for="pais">País:</label>
            <input type="text" v-model="nuevaEmpresa.pais" />
            <span class="tooltip-text">El país debe tener entre 2 y 50 caracteres</span>
          </div>
          <div class="form-group button-group">
            <button type="submit">Guardar</button>
            <button type="button" @click="resetForm">Limpiar</button>
            <button type="button" @click="cerrarModalCrearEmpresa">Cancelar</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      empresas: [],
      filteredEmpresas: [],
      empleados: [],
      search: '',
      toastVisible: false,
      toastMessage: '',
      empleadosVisible: false,
      modalDetallesVisible: false,
      modalEditarVisible: false,
      modalBorrarVisible: false,
      modalEditarEmpresaVisible: false,  
      empresaSeleccionada: null,
      empleadoSeleccionado: null,
      modalBorrarEmpresaVisible: false,
      modalCrearEmpresaVisible: false,
      nuevaEmpresa: {
        nombre: '',
        cif: '',
        direccion: '',
        telefono: '',
        email: '',
        paginaWeb: '',
        sector: '',
        pais: ''
      }
    };
  },
  created() {
    this.fetchEmpresas();
    this.fetchEmpleados();
  },
  methods: {
    async fetchEmpresas() {
      try {
        const response = await axios.get('/api/empresas');
        this.empresas = response.data;
        this.filteredEmpresas = [...this.empresas];
      } catch (error) {
        console.error('Error al obtener las empresas:', error);
      }
    },

    async fetchEmpleados() {
      try {
        const response = await axios.get('/api/empleados');
        this.empleados = response.data;
      } catch (error) {
        console.error('Error al obtener los empleados:', error);
      }
    },

    abrirModalEditarEmpresa(empresa) {
      this.empresaSeleccionada = { ...empresa };
      this.modalEditarEmpresaVisible = true;
    },

    cerrarModalEditarEmpresa() {
      this.modalEditarEmpresaVisible = false;
    },

    cerrarModalCrearEmpresa() {
      this.modalCrearEmpresaVisible = false;
    },

    async guardarCambiosEmpresa() {
      try {
        await axios.put(`/api/empresas/${this.empresaSeleccionada.id}`, this.empresaSeleccionada);
        this.toastMessage = 'Empresa actualizada correctamente';
        this.toastVisible = true;
        
        setTimeout(() => {
          this.toastVisible = false;
        }, 3000);

        this.cerrarModalEditarEmpresa();
        this.fetchEmpresas();
      } catch (error) {
        console.error('Error al actualizar la empresa:', error);
        this.toastMessage = 'Error al actualizar la empresa';
        this.toastVisible = true;

        setTimeout(() => {
          this.toastVisible = false;
        }, 3000);
      }
    },

    abrirModalCrearEmpresa() {
      this.modalCrearEmpresaVisible = true;
    },

    filterByName() {
      const searchLower = this.search.toLowerCase();
      this.filteredEmpresas = this.empresas.filter(empresa =>
        empresa.nombre.toLowerCase().startsWith(searchLower)
      );
    },

    resetForm() {
      this.nuevaEmpresa = {
        nombre: '',
        cif: '',
        direccion: '',
        telefono: '',
        email: '',
        paginaWeb: '',
        sector: '',
        pais: ''
      };
    },

    cerrarModalCrearEmpresa() {
      this.modalCrearEmpresaVisible = false;
    },

    sortAlphabetically() {
      this.filteredEmpresas.sort((a, b) => a.nombre.localeCompare(b.nombre));
    },

    resetFilters() {
      this.search = '';
      this.filteredEmpresas = [...this.empresas];
    },

    mostrarEmpleados(empresa) {
      this.empresaSeleccionada = empresa;
      this.empleados = empresa.empleados;
      this.empleadosVisible = true;
    },

    cerrarModalEmpleados() {
      this.empleadosVisible = false;
    },

    verDetalles(empleado) {
      this.empleadoSeleccionado = { ...empleado };
      this.modalDetallesVisible = true;
    },

    editarEmpleado(empleado) {
      this.empleadoSeleccionado = { ...empleado };
      this.modalEditarVisible = true;
    },

    async guardarCambiosEmpleado() {
  try {
    const response = await axios.put(`/api/empleados/${this.empleadoSeleccionado.id}`, this.empleadoSeleccionado);
    this.toastMessage = 'Empleado actualizado correctamente';
    this.toastVisible = true;
    this.modalEditarVisible = false;
    setTimeout(() => this.toastVisible = false, 3000);
  
  } catch (error) {
    console.error('Error al actualizar el empleado:', error);
  }
},

confirmarBorrarEmpleado(empleado) {
  this.empleadoSeleccionado = { ...empleado };
  this.modalBorrarVisible = true;
},

async borrarEmpleado() {
  try {
    const response = await axios.delete(`/api/empleados/${this.empleadoSeleccionado.id}`);
    this.toastMessage = 'Empleado eliminado correctamente';
    this.toastVisible = true;
    this.modalBorrarVisible = false;
    setTimeout(() => this.toastVisible = false, 3000);

  } catch (error) {
    console.error('Error al eliminar el empleado:', error);
  }
    },

    cerrarModalDetalles() {
      this.modalDetallesVisible = false;
    },

    cerrarModalEditar() {
      this.modalEditarVisible = false;
    },

    cerrarModalBorrar() {
      this.modalBorrarVisible = false;
    },

    async crearEmpresa() {
      try {
        await axios.post('/api/empresas', this.nuevaEmpresa);
        this.toastMessage = 'Empresa creada correctamente';
        this.toastVisible = true;
        setTimeout(() => this.toastVisible = false, 3000);
        this.fetchEmpresas();
        this.cerrarModalCrearEmpresa();
      } catch (error) {
        console.error('Error al crear la empresa:', error);
        this.toastMessage = 'Error al crear la empresa';
        this.toastVisible = true;
        setTimeout(() => this.toastVisible = false, 3000);
      }
    },

    confirmarBorrarEmpresa(empresa) {
      this.empresaSeleccionada = { ...empresa };
      this.modalBorrarEmpresaVisible = true;
    },

    cerrarModalBorrarEmpresa() {
      this.modalBorrarEmpresaVisible = false;
    },

    async borrarEmpresa() {
      try {
        await axios.delete(`/api/empresas/${this.empresaSeleccionada.id}`);
        this.toastMessage = 'Empresa eliminada correctamente';
        this.toastVisible = true;

        setTimeout(() => {
          this.toastVisible = false;
        }, 3000);

        this.cerrarModalBorrarEmpresa();
        this.fetchEmpresas(); // Refrescar la lista de empresas
        this.fetchEmpleados(); // Refrescar la lista de empleados
      } catch (error) {
        console.error('Error al eliminar la empresa:', error);
        if (error.response && error.response.status === 409) {
          this.toastMessage = 'No se puede eliminar la empresa porque tiene empleados asociados.';
        } else {
          this.toastMessage = 'Error al eliminar la empresa';
        }
        this.toastVisible = true;

        setTimeout(() => {
          this.toastVisible = false;
        }, 3000);

        this.fetchEmpleados(); // Refrescar la lista de empleados
      }
    }
  }
};
</script>

<style scoped>
.toast {
  z-index: 9999;
  background-color: #5c99d6;
  color: white;
  padding: 10px;
  position: fixed;
  bottom: 20px;
  right: 20px;
  border-radius: 5px;
}

.empleados-modal {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: white;
  padding: 20px;
  z-index: 1000;
  width: 80%;
  max-width: 600px;
  border-radius: 8px;
  background-color: rgba(200, 200, 200, 0.8); ; 
}

.empleados-modal h2 {
  margin-bottom: 20px;
  font-size: 20px;
  
}

.table-container {
  overflow-x: auto;
}

.filters-container {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

button {
  background-color: #5c99d6;
  color: white;
  border: none;
  padding: 10px;
  cursor: pointer;
  border-radius: 4px;
}

.action-button {
  background-color: #4caf50;; 
  color: white;
}

.action-button:hover {
  background-color:  #4caf50;; 
}
button:hover {
  background-color: #4a87c4;
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
  background-color: rgba(200, 200, 200, 0.8); 
  z-index: 1050;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 5px;
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

.form-group label {
  margin-bottom: 5px;
  font-weight: bold;
}

.form-group input[type="text"],
.form-group input[type="email"],
.form-group input[type="date"] {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}

.button-group {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.button-group button {
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 4px;
  border: none;
  flex: 1;
  margin-right: 10px;
  /* Espacio entre botones */
}

.button-group button:last-child {
  margin-right: 0;
}

.modal-content {
  display: flex;
  justify-content: space-between;
}


button[type="submit"] {
  background-color: #5c99d6;
  color: white;
}

button[type="button"] {
  background-color: #d3d3d3;
  color: black;
}

button[type="submit"]:hover {
  background-color: #4a87c4;
}

button[type="button"]:hover {
  background-color: #c1c1c1;
}

.title {
  text-align: center;
  color: #5c99d6;
  text-decoration: underline;
  margin-bottom: 20px;
}

.tooltip-container {
  position: relative;
  display: inline-block;
}

.tooltip-text {
  visibility: hidden;
  background-color: black;
  color: #fff;
  text-align: center;
  border-radius: 6px;
  padding: 5px;
  position: absolute;
  z-index: 1;
  bottom: 125%;
  left: 50%;
  transform: translateX(-50%);
  opacity: 0;
  transition: opacity 0.3s;
  white-space: nowrap;
  font-size: 12px;
}

/* Mostrar tooltip al pasar el cursor */
.tooltip-container:hover .tooltip-text {
  visibility: visible;
  opacity: 1;
}

.modal-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding: 20px;
  background-color: #fff;
  border-radius: 5px;
  max-width: 400px; /* Ajuste para evitar un ancho excesivo */
  width: 100%;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  position: relative;
}

h2 {
  margin-bottom: 20px;
  font-size: 18px;
  text-align: center; /* Centramos el título */
}

p {
  margin: 8px 0;
  line-height: 1.5;
  word-wrap: break-word; 
}

button {
  align-self: center; 
  background-color: #5c99d6;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 20px;
}

button:hover {
  background-color: #4a87c4;
}

.modal-content p strong {
  display: inline-block;
  width: 100px; 
}

.modal-content p span {
  display: inline-block;
  max-width: 250px; 
  word-wrap: break-word; 
}


</style>
