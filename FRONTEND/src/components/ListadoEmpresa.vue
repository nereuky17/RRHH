<template>
  <div v-if="toastVisible" class="toast">
    {{ toastMessage }}
  </div>
  <div>
    <h1 class="title"><br>Gestión de Empresas<br></h1>

    <!-- Botón de menú desplegable -->
    <div class="menu-dropdown">
      <button @click="toggleMenu" class="menu-button">☰</button>
      <div v-if="menuVisible" class="dropdown-content">
        <input type="text" v-model="search" placeholder="Filtrar por nombre" @input="filterByName" />
        <button @click="sortAlphabetically">Ordenar Alfabéticamente</button>
        <button @click="resetFilters">Resetear Filtros</button>
      </div>
    </div>

    <div class="button-container">
      <button class="create-button" @click="abrirModalCrearEmpresa">Crear Empresa</button>
    </div>
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
              <ul>
                <li v-for="empleado in empresa.empleados" :key="empleado.id">
                  {{ empleado.nombre }} {{ empleado.apellido }}
                  <div class="button-group">
                    <button @click="verDetalles(empleado)" class="tooltip">Ver detalles
                      <span class="tooltip-text">Ver los detalles del empleado</span>
                    </button>
                    <button @click="editarEmpleado(empleado)" class="tooltip">Editar
                      <span class="tooltip-text">Editar los datos del empleado</span>
                    </button>
                    <button @click="borrarEmpleado(empleado)" class="tooltip">Borrar
                      <span class="tooltip-text">Eliminar al empleado</span>
                    </button>
                  </div>
                </li>
              </ul>
            </td>
          </tr>
        </tbody>
      </table>

      <!-- Modal para crear empresa -->
      <div v-if="modalCrearEmpresaVisible" class="modal" @click.self="cerrarModalCrearEmpresa">
        <div class="modal-content">
          <span class="close" @click="cerrarModalCrearEmpresa">&times;</span>
          <h2>Crear Nueva Empresa</h2>
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
            <div class="form-group">
              <button type="submit">Guardar Empresa</button>
            </div>
          </form>
        </div>
      </div>
    </div>


    <!-- Modal de detalles -->
    <div v-if="showModal" class="modal" @click.self="closeModal">
      <div class="modal-content">
        <span class="close" @click="closeModal">&times;</span>
        <h2>Detalles de {{ selectedEmpleado.nombre }} {{ selectedEmpleado.apellido }}</h2>
        <p><strong>Email:</strong> {{ selectedEmpleado.email }}</p>
        <p><strong>Teléfono:</strong> {{ selectedEmpleado.telefono }}</p>
        <p><strong>Posición:</strong> {{ selectedEmpleado.posicion }}</p>
        <p><strong>Fecha de Contratación:</strong> {{ selectedEmpleado.fechaContratacion }}</p>
      </div>
    </div>

    <!-- Modal de edición -->
    <div v-if="showEditModal" class="modal" @click.self="closeEditModal">
      <div class="modal-content">
        <span class="close" @click="closeEditModal">&times;</span>
        <h2>Editar {{ selectedEmpleado.nombre }} {{ selectedEmpleado.apellido }}</h2>
        <form @submit.prevent="guardarCambios">
          <p><strong>Nombre:</strong> <input type="text" v-model="selectedEmpleado.nombre" /></p>
          <p><strong>Apellido:</strong> <input type="text" v-model="selectedEmpleado.apellido" /></p>
          <p><strong>Email:</strong> <input type="email" v-model="selectedEmpleado.email" /></p>
          <p><strong>Teléfono:</strong> <input type="text" v-model="selectedEmpleado.telefono" /></p>
          <p><strong>Posición:</strong> <input type="text" v-model="selectedEmpleado.posicion" /></p>
          <p><strong>Fecha de Contratación:</strong> <input type="date" v-model="selectedEmpleado.fechaContratacion" />
          </p>
          <button type="submit">Guardar Cambios</button>
        </form>
      </div>
    </div>

    <!-- Modal de confirmación de eliminación -->
    <div v-if="showDeleteModal" class="modal" @click.self="closeDeleteModal">
      <div class="modal-content">
        <span class="close" @click="closeDeleteModal">&times;</span>
        <h2>¿Eliminar a {{ selectedEmpleado.nombre }} {{ selectedEmpleado.apellido }}?</h2>
        <p>Esta acción no se puede deshacer.</p>
        <button class="button-delete" @click="eliminarEmpleado">Eliminar</button>
        <button class="button-cancel" @click="closeDeleteModal">Cancelar</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  directives: {
    tooltip: {
      bind(el, binding) {
        const span = document.createElement('span');
        span.className = 'tooltip-text';
        span.innerText = binding.value;
        el.classList.add('tooltip-container');
        el.appendChild(span);

        el.onmouseenter = () => {
          span.style.display = 'inline';
        };
        el.onmouseleave = () => {
          span.style.display = 'none';
        };
      }
    }
  },
  data() {
    return {
      empresas: [],
      filteredEmpresas: [],
      menuVisible: false,
      search: '',
      toastVisible: false,
      toastMessage: '',
      showModal: false,
      showEditModal: false,
      showDeleteModal: false,
      selectedEmpleado: {},
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
  },
  methods: {
    async fetchEmpresas() {
      try {
        const response = await axios.get('/api/empresas');
        this.empresas = response.data;
        this.filteredEmpresas = this.empresas;
      } catch (error) {
        console.error('Error al obtener las empresas:', error);
      }
    },

    toggleMenu() {
      this.menuVisible = !this.menuVisible;
    },

    filterByName() {
      const searchLower = this.search.toLowerCase();
      this.filteredEmpresas = this.empresas.filter(empresa =>
        empresa.nombre.toLowerCase().startsWith(searchLower)
      );
    },

    sortAlphabetically() {
      this.filteredEmpresas.sort((a, b) => a.nombre.localeCompare(b.nombre));
    },

    resetFilters() {
      this.search = '';
      this.fetchEmpresas();
    },

    async verDetalles(empleado) {
      try {
        const response = await axios.get(`/api/empleados/${empleado.id}`);
        this.selectedEmpleado = response.data;
        this.showModal = true;
      } catch (error) {
        console.error('Error al obtener detalles del empleado:', error);
      }
    },

    closeModal() {
      this.showModal = false;
    },

    async editarEmpleado(empleado) {
      this.selectedEmpleado = empleado;
      this.showEditModal = true;
    },

    async borrarEmpleado(empleado) {
      this.selectedEmpleado = empleado;
      this.showDeleteModal = true;
    },

    async eliminarEmpleado() {
      try {
        await axios.delete(`/api/empleados/${this.selectedEmpleado.id}`);
        this.toastMessage = `Empleado ${this.selectedEmpleado.nombre} ${this.selectedEmpleado.apellido} eliminado`;
        this.toastVisible = true;

        setTimeout(() => {
          this.toastVisible = false;
        }, 3000);

        this.showDeleteModal = false;
        this.fetchEmpresas();
      } catch (error) {
        console.error('Error al eliminar empleado:', error);
      }
    },

    closeDeleteModal() {
      this.showDeleteModal = false;
    },

    async guardarCambios() {
      try {
        await axios.put(`/api/empleados/${this.selectedEmpleado.id}`, {
          nombre: this.selectedEmpleado.nombre,
          apellido: this.selectedEmpleado.apellido,
          email: this.selectedEmpleado.email,
          telefono: this.selectedEmpleado.telefono,
          posicion: this.selectedEmpleado.posicion,
          fechaContratacion: this.selectedEmpleado.fechaContratacion
        });

        this.toastMessage = `Empleado ${this.selectedEmpleado.nombre} actualizado correctamente`;
        this.toastVisible = true;

        setTimeout(() => {
          this.toastVisible = false;
        }, 3000);

        this.showEditModal = false;
        this.fetchEmpresas();
      } catch (error) {
        console.error('Error al guardar los cambios del empleado:', error);
      }
    },

    abrirModalCrearEmpresa() {
      this.modalCrearEmpresaVisible = true;
    },

    cerrarModalCrearEmpresa() {
      this.modalCrearEmpresaVisible = false;
    },

    async crearEmpresa() {
      try {
        const response = await axios.post('/api/empresas', this.nuevaEmpresa);
        this.toastMessage = `Empresa ${this.nuevaEmpresa.nombre} creada correctamente`;
        this.toastVisible = true;

        setTimeout(() => {
          this.toastVisible = false;
        }, 3000);

        this.modalCrearEmpresaVisible = false;
        this.fetchEmpresas();
      } catch (error) {
        console.error('Error al crear empresa:', error);
      }
    }
  }
};
</script>

<style scoped>
.form-group {
  margin-bottom: 15px;
  /* Espacio entre campos del formulario */
  display: flex;
  flex-direction: column;
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

button[type="submit"] {
  background-color: #000080;
  color: white;
  padding: 12px 20px;
  border: none;
  cursor: pointer;
  font-size: 16px;
  margin-top: 10px;
  /* Espacio arriba del botón */
}

button[type="submit"]:hover {
  background-color: #000066;
}

.title {
  text-align: center;
  color: #000080;
  text-decoration: underline;
  margin-bottom: 20px;
}

.table-container {
  overflow-x: auto;
  width: 100%;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  border: 1px solid #000080;
  padding: 8px;
  text-align: left;
}

th {
  background-color: #000080;
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

td:last-child {
  width: 200px;
}

h1 {
  color: #000080;
  text-decoration: underline;
}

.menu-dropdown {
  position: relative;
  display: inline-block;
}

.menu-button {
  background-color: #000080;
  color: white;
  padding: 10px;
  border: none;
  cursor: pointer;
  font-size: 16px;
}

.dropdown-content {
  display: block;
  position: absolute;
  background-color: white;
  min-width: 200px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 1;
}

.dropdown-content input {
  width: 100%;
  padding: 10px;
  box-sizing: border-box;
}

.dropdown-content button {
  background-color: #000080;
  color: white;
  padding: 10px;
  width: 100%;
  border: none;
  cursor: pointer;
  text-align: left;
}

.dropdown-content button:hover {
  background-color: #000066;
}

td button {
  padding: 5px 10px;
  font-size: 14px;
  cursor: pointer;
  width: 100px;
  margin-right: 5px;
}





.toast {
  z-index: 9999;
  background-color: #000080;
  color: white;
  padding: 10px;
  position: fixed;
  bottom: 20px;
  right: 20px;
  border-radius: 5px;
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

.button-delete {
  background-color: #b22222;
  color: white;
  padding: 10px;
  border: none;
  cursor: pointer;
  border-radius: 5px;
}

.button-delete:hover {
  background-color: #8b0000;
}

.button-cancel {
  background-color: #d3d3d3;
  color: black;
  padding: 10px;
  border: none;
  cursor: pointer;
  border-radius: 5px;
  margin-left: 10px;
}

.button-cancel:hover {
  background-color: #000080;
}

.button-container {
  text-align: center;
  margin: 20px 0;
}

button.create-button {
  background-color: #000080;
  color: white;
  padding: 10px 20px;
  border: none;
  cursor: pointer;
  font-size: 16px;
  margin: 0 10px;
}

button.create-button:hover {
  background-color: #000066;
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


.tooltip-container:hover .tooltip-text {
  visibility: visible;
  opacity: 1;
}

button.tooltip {
  background-color: #000080;
  /* Color de fondo azul */
  color: white;
  padding: 10px 20px;
  border: none;
  cursor: pointer;
  font-size: 14px;
  margin-right: 5px;
  border-radius: 4px;
  position: relative;
  display: inline-block;
}

button.tooltip:hover {
  background-color: #000066;
}

button.tooltip .tooltip-text {
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
}

button.tooltip:hover .tooltip-text {
  visibility: visible;
  opacity: 1;
}

.button-group {
  display: flex;
  gap: 10px; /* Ajusta el espacio entre los botones */
}

.button-group button {
  flex: 1; /* Hace que los botones se ajusten al tamaño del contenedor */
  padding: 5px 10px;
  font-size: 14px;
  cursor: pointer;
}
</style>
