import { mount } from 'cypress/vue'
import InsertarEmpresa from '@/components/ListadoEmpresa.vue'
describe('ListadoEmpresa', () => {
    it('Debería mostrar el listado de empresas', () => {
        mount(InsertarEmpresa)
      cy.visit('/listado')
      cy.get('table').should('exist')
      cy.get('tbody tr').should('have.length.greaterThan', 0)
    })
  
    it('Debería filtrar empresas por nombre', () => {
        mount(InsertarEmpresa)
      cy.visit('/listado')
      cy.get('input[placeholder="Filtrar por nombre"]').type('Empresa A')
      cy.get('tbody tr').each(($row) => {
        cy.wrap($row).should('contain.text', 'Empresa A')
      })
    })
  
    it('Debería resetear los filtros', () => {
        mount(InsertarEmpresa)
      cy.visit('/listado')
      cy.get('input[placeholder="Filtrar por nombre"]').type('Empresa A')
      cy.get('button').contains('Resetear Filtros').click()
      cy.get('input[placeholder="Filtrar por nombre"]').should('have.value', '')
      cy.get('tbody tr').should('have.length.greaterThan', 0)
    })
  
    it('Debería ordenar empresas alfabéticamente', () => {
        mount(InsertarEmpresa)
      cy.visit('/listado')
      cy.get('button').contains('Ordenar Alfabéticamente').click()
      cy.get('tbody tr td:first-child').then(($cells) => {
        const names = $cells.map((index, el) => Cypress.$(el).text()).get()
        const sortedNames = [...names].sort()
        expect(names).to.deep.equal(sortedNames)
      })
    })
  
    it('Debería abrir el modal de creación de empresa', () => {
        mount(InsertarEmpresa)
      cy.visit('/listado')
      cy.get('button').contains('Crear').click()
      cy.get('.modal').should('exist')
    })
  })
  