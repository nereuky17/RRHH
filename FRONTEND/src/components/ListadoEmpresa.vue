<template>
  <div>
    <h1 class="title"><br>Gestión de Empresas<br></h1>

    <!-- Botón de menú desplegable -->
    <div class="menu-dropdown">
      <button @click="toggleMenu" class="menu-button">☰</button>
      <div v-if="menuVisible" class="dropdown-content">
        <input type="text" v-model="search" placeholder="Filtrar por nombre" @input="filterByName" />
        <button @click="sortAlphabetically">Ordenar Alfabéticamente</button>
      </div>
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
                <!-- Botones para cada empleado -->
                <button @click="verDetalles(empleado)" v-tooltip="'Ver detalles'">Ver detalles</button>
                <button @click="editarEmpleado(empleado)" v-tooltip="'Editar'">Editar</button>
                <button @click="borrarEmpleado(empleado)" v-tooltip="'Borrar'">Borrar</button>
              </li>
            </ul>
          </td>
        </tr>
      </tbody>
    </table>
  </div>

    <div v-if="toastVisible" class="toast">
      {{ toastMessage }}
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
      menuVisible: false,
      search: '',
      toastVisible: false,
      toastMessage: '',
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
        empresa.nombre.toLowerCase().startsWith(searchLower) // Cambia includes() por startsWith()
      );
    },
    sortAlphabetically() {
      this.filteredEmpresas.sort((a, b) => a.nombre.localeCompare(b.nombre));
    },
    async verDetalles(empleado) {
      try {
        const response = await axios.get(`/api/empleados/${empleado.id}`);
        const detalles = response.data;
        alert(`Detalles de ${detalles.nombre} ${detalles.apellido}: Email: ${detalles.email}, Teléfono: ${detalles.telefono}`);
      } catch (error) {
        console.error('Error al obtener detalles del empleado:', error);
      }
    },
    async editarEmpleado(empleado) {
      alert(`Editar a ${empleado.nombre} ${empleado.apellido}`);
    },
    async borrarEmpleado(empleado) {
      if (confirm(`¿Estás seguro de que quieres borrar a ${empleado.nombre} ${empleado.apellido}?`)) {
        try {
          await axios.delete(`/api/empleados/${empleado.id}`);
          this.toastMessage = `Empleado ${empleado.nombre} ${empleado.apellido} eliminado`;
          this.toastVisible = true;
          setTimeout(() => {
            this.toastVisible = false;
          }, 3000);
          this.fetchEmpresas();
        } catch (error) {
          console.error('Error al borrar empleado:', error);
        }
      }
    }
  }
};
</script>

<style scoped>


.title {
  text-align: center;
  color: #000080; 
  text-decoration: underline;
  margin-bottom: 20px;
}


.table-container {
  max-height: 400px;
  overflow-y: auto; 
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


.toast {
  background-color: #000080;
  color: white;
  padding: 10px;
  position: fixed;
  bottom: 20px;
  right: 20px;
  border-radius: 5px;
}

td button {
  padding: 5px 10px;
  font-size: 14px;
  cursor: pointer;
  width: 100px; 
  margin-right: 5px;
}


[tooltip] {
  position: relative;
  cursor: pointer;
}

[tooltip]:before {
  content: attr(tooltip);
  position: absolute;
  bottom: 100%;
  left: 50%;
  transform: translateX(-50%);
  background-color: black;
  color: white;
  padding: 5px;
  border-radius: 3px;
  white-space: nowrap;
  display: none;
}

[tooltip]:hover:before {
  display: block;
}
</style>
