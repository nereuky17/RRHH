import { mount } from 'cypress/vue'
import InsertarEmpleado from '@/components/InsertarEmpleado.vue'

describe('InsertarEmpleado', () => {
  it('Debería abrir el modal de creación de empleado', () => {
    mount(InsertarEmpleado)
    cy.get('button').contains('Insertar Empleado').click({ force: true })
    cy.get('.modal').should('exist')
  })

  it('Debería mostrar errores si los campos están vacíos', () => {
    mount(InsertarEmpleado)
    cy.get('button').contains('Insertar Empleado').click({ force: true })
    cy.get('button[type="submit"]').click({ force: true })
    cy.get('.form-group input:invalid').should('have.length.greaterThan', 0)
  })

  it('Debería crear un empleado con datos válidos', () => {
    mount(InsertarEmpleado)
    cy.get('button').contains('Insertar Empleado').click({ force: true })
    
    cy.contains('label', 'Nombre').next('input').type('Juan ')
    cy.contains('label', 'Apellido').next('input').type('García')
    cy.contains('label', 'DNI').next('input').type('12345623X')
    cy.contains('label', 'Email').next('input').type('juan.pperez@example.com')
    cy.contains('label', 'Teléfono').next('input').type('123456798')
    cy.contains('label', 'Posición').next('input').type('Desarrollador')
    cy.get('select').select('TechSolutions')
    cy.get('button[type="submit"]').click({ force: true })

    // Esperar que el toast aparezca y verificar su contenido
    cy.get('.toast', { timeout: 10000 }).should('be.visible').and('contain.text', 'Empleado creado correctamente')
  })
})
