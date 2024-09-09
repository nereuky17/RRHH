<template>
  <div>
      <h1>Listado de Empresas</h1>

      <!-- Botón para mostrar/ocultar los filtros -->
      <button @click="toggleFiltros">Filtros</button>

      <!-- Sección de filtros -->
      <div v-if="mostrarFiltros">
          <h2>Filtros</h2>

          <!-- Filtro de búsqueda por nombre de empresa -->
          <div>
              <label for="nombre">Buscar por nombre de empresa:</label>
              <input type="text" v-model="searchName" placeholder="Nombre de la empresa" />
              <button @click="buscarPorNombre">Buscar</button>
          </div>

          <!-- Ordenar empresas alfabéticamente -->
          <div>
              <label>Ordenar empresas:</label>
              <button @click="ordenarAlfabeticamente">Ordenar A-Z</button>
              <button @click="resetOrden">Reiniciar Orden</button>
          </div>
      </div>

      <!-- Tabla de empresas -->
      <table class="details-table">
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
                  <th>Acciones</th>
              </tr>
          </thead>
          <tbody>
              <tr v-for="empresa in empresasFiltradas" :key="empresa.id">
                  <td>{{ empresa.nombre }}</td>
                  <td>{{ empresa.cif }}</td>
                  <td>{{ empresa.direccion }}</td>
                  <td>{{ empresa.telefono }}</td>
                  <td>{{ empresa.email }}</td>
                  <td>{{ empresa.pagina_web }}</td>
                  <td>{{ empresa.sector }}</td>
                  <td>{{ empresa.pais }}</td>
                  <td>
                      <button @click="mostrarEmpleados(empresa)">Ver Empleados</button>
                  </td>
              </tr>
              <tr v-if="empresa.mostrarEmpleados" v-for="empleado in empresa.empleados" :key="empleado.id"
                  class="empleado-row">
                  <td colspan="9">
                      <div class="empleado-card">
                          <img src="@/assets/empleado.png" alt="Foto del empleado" class="empleado-imagen">
                          <div class="empleado-info">
                              <p><strong>Nombre:</strong> {{ empleado.nombre }}</p>
                              <p><strong>Apellido:</strong> {{ empleado.apellido }}</p>
                              <p><strong>Posición:</strong> {{ empleado.posicion }}</p>
                              <p><strong>Email:</strong> {{ empleado.email }}</p>
                          </div>
                      </div>
                  </td>
              </tr>
          </tbody>
      </table>

      <!-- Modal para detalles del empleado -->
      <div v-if="showModal" class="modal" @click.self="closeModal">
          <div class="modal-content">
              <span class="close" @click="closeModal">&times;</span>
              <div>
                  <h2>Detalles del Empleado</h2>
                  <table>
                      <tbody>
                          <tr>
                              <td>Nombre:</td>
                              <td>{{ empleadoSeleccionado.nombre }}</td>
                          </tr>
                          <tr>
                              <td>Apellido:</td>
                              <td>{{ empleadoSeleccionado.apellido }}</td>
                          </tr>
                          <tr>
                              <td>Posición:</td>
                              <td>{{ empleadoSeleccionado.posicion }}</td>
                          </tr>
                          <tr>
                              <td>Email:</td>
                              <td>{{ empleadoSeleccionado.email }}</td>
                          </tr>
                      </tbody>
                  </table>
              </div>
          </div>
      </div>
  </div>
</template>

<script>
import axios from "axios";
import { ref, computed } from "vue";

export default {
  setup() {
      const empresas = ref([]);
      const empleados = ref([]);
      const empresaSeleccionada = ref(null);
      const empleadoSeleccionado = ref(null);
      const searchName = ref(""); // Para búsqueda por nombre
      const mostrarFiltros = ref(false); // Mostrar/ocultar filtros
  
      const showModal = ref(false);

      const empresasFiltradas = computed(() => {
        if (searchName.value) {
          return empresas.value.filter(empresa =>
            empresa.nombre.toLowerCase().includes(searchName.value.toLowerCase())
          );
        }
        return empresas.value;
      });

      const fetchEmpresas = async () => {
        try {
          const response = await axios.get('/api/empresas');
          empresas.value = response.data;
        } catch (error) {
          console.error('Error al obtener empresas:', error);
        }
      };

      const mostrarEmpleados = async (empresaId) => {
        try {
          const response = await axios.get(`/api/empleados?empresaId=${empresaId}`);
          empleados.value = response.data;
        } catch (error) {
          console.error('Error al obtener empleados:', error);
        }
      };

      // Buscar por nombre de empresa
      const buscarPorNombre = () => {
          if (searchName.value) {
              empresasFiltradas.value = empresas.value.filter((empresa) =>
                  empresa.nombre.toLowerCase().includes(searchName.value.toLowerCase())
              );
          }
      };

      // Ordenar empresas alfabéticamente
      const ordenarAlfabeticamente = () => {
          empresasFiltradas.value = [...empresasFiltradas.value].sort((a, b) =>
              a.nombre.localeCompare(b.nombre)
          );
      };

      // Resetear orden y filtros
      const resetOrden = () => {
          empresasFiltradas.value = empresas.value;
          searchName.value = "";
      };

      // Mostrar/ocultar los filtros
      const toggleFiltros = () => {
          mostrarFiltros.value = !mostrarFiltros.value;
      };

      // Ver detalles del empleado
      const verDetallesEmpleado = (empleado) => {
          empleadoSeleccionado.value = empleado;
          showModal.value = true;
      };

      // Cerrar modal de detalles
      const closeModal = () => {
          showModal.value = false;
      };

      // Editar empleado
      const editarEmpleado = (empleado) => {
          console.log("Editar empleado:", empleado);
          // Redirigir o mostrar formulario de edición
      };

      // Eliminar empleado
      const eliminarEmpleado = async (empleadoId) => {
          try {
              await axios.delete(`/api/empleados/${empleadoId}`);
              mostrarEmpleados(empresaSeleccionada.value); // Refrescar lista de empleados
          } catch (error) {
              console.error("Error al eliminar empleado:", error);
          }
      };

      fetchEmpresas(); // Obtener empresas al montar el componente

      return {
          empresas,
          empleados,
          empresaSeleccionada,
          empleadoSeleccionado,
          searchName,
          empresasFiltradas,
          mostrarFiltros,
          showModal,
          fetchEmpresas,
          mostrarEmpleados,
          verDetallesEmpleado,
          editarEmpleado,
          eliminarEmpleado,
          closeModal,
          buscarPorNombre,
          ordenarAlfabeticamente,
          resetOrden,
          toggleFiltros,
      };
  },
};
</script>

<style scoped>
.details-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

.details-table th,
.details-table td {
  border: 1px solid #ddd;
  padding: 8px;
}

.details-table th {
  background-color: #f2f2f2;
}

button {
  margin: 5px;
}

input {
  margin: 10px 0;
}

.empleado-row {
  background-color: #f9f9f9;
}

.empleado-card {
  display: flex;
  align-items: center;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  margin-bottom: 10px;
}

.empleado-imagen {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 10px;
}

.empleado-info {
  display: flex;
  flex-direction: column;
}

.modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
  background-color: #fff;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
  text-align: center;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}
</style>