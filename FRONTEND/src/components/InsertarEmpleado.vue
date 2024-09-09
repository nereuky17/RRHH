<template>
    <div>
      <h1>Insertar Nuevo Empleado</h1>
  
      <form @submit.prevent="crearEmpleado">
        <label for="empresa">Empresa:</label>
        <select v-model="nuevoEmpleado.idEmpresa" required>
          <option v-for="empresa in empresas" :key="empresa.id" :value="empresa.id">
            {{ empresa.nombre }}
          </option>
        </select>
  
        <label for="nombre">Nombre:</label>
        <input type="text" v-model="nuevoEmpleado.nombre" required />
  
        <label for="apellido">Apellido:</label>
        <input type="text" v-model="nuevoEmpleado.apellido" />
  
        <label for="dni">DNI:</label>
        <input type="text" v-model="nuevoEmpleado.dni" required />
  
        <label for="email">Email:</label>
        <input type="email" v-model="nuevoEmpleado.email" required />
  
        <label for="telefono">Teléfono:</label>
        <input type="text" v-model="nuevoEmpleado.telefono" required />
  
        <label for="posicion">Posición:</label>
        <input type="text" v-model="nuevoEmpleado.posicion" required />
  
        <label for="fechaContratacion">Fecha de Contratación:</label>
        <input type="date" v-model="nuevoEmpleado.fechaContratacion" required />
  
        <button type="submit">Agregar Empleado</button>
      </form>
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
  
      // Obtener empresas
      const fetchEmpresas = async () => {
        try {
          const response = await axios.get('/api/empresas');
          empresas.value = response.data;
        } catch (error) {
          console.error('Error al obtener empresas:', error);
        }
      };
  
      // Crear nuevo empleado
      const crearEmpleado = async () => {
        try {
          await axios.post(`/api/empleados/empresa/${nuevoEmpleado.value.idEmpresa}`, nuevoEmpleado.value);
          alert('Empleado agregado correctamente');
        } catch (error) {
          console.error('Error al crear empleado:', error);
        }
      };
  
      fetchEmpresas(); 
  
      return {
        empresas,
        nuevoEmpleado,
        crearEmpleado
      };
    }
  };
  </script>
  