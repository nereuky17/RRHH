import { mount } from "cypress/vue";
import ListadoEmpresa from "@/components/ListadoEmpresa.vue";

describe("ListadoEmpresa", () => {
  // Interceptar la API de empresas y simular una respuesta
  beforeEach(() => {
    cy.intercept("GET", "/api/empresas", {
      statusCode: 200,
      body: [
        {
          id: 1,
          nombre: "AI Innovators",
          cif: "Z99887766",
          direccion: "Calle Inteligencia 10",
          telefono: "654987321",
          email: "info@aiinnovators.com",
          paginaWeb: "www.aiinnovators.com",
          sector: "Inteligencia Artificial",
          pais: "Argentina",
        },
        {
          id: 2,
          nombre: "TechSolutions",
          cif: "B12345678",
          direccion: "Calle Falsa 123",
          telefono: "987654321",
          email: "contacto@techsolutions.com",
          paginaWeb: "www.techsolutions.com",
          sector: "Tecnología",
          pais: "España",
        },
      ],
    }).as("getEmpresas");
  });

  it("Debería mostrar el listado de empresas", () => {
    mount(ListadoEmpresa);
    cy.wait("@getEmpresas");
    cy.get("table tbody tr").should("have.length", 2);
  });

  it("Debería abrir el modal de creación de empresa", () => {
    mount(ListadoEmpresa);
    cy.get("button").contains("Crear").click({ force: true });
    cy.get(".modal").should("exist");
  });
  it("Debería crear una empresa con datos válidos", () => {
    mount(ListadoEmpresa);

    cy.get("button").contains("Crear").click({ force: true });

    cy.get(".modal").should("exist");

    cy.contains("label", "Nombre").next("input").type("TechSolutions");
    cy.contains("label", "CIF").next("input").type("B12345678");
    cy.contains("label", "Dirección").next("input").type("Calle Falsa 123");
    cy.contains("label", "Teléfono").next("input").type("987654321");
    cy.contains("label", "Email")
      .next("input")
      .type("contacto@techsolutions.com");
    cy.contains("label", "Página Web")
      .next("input")
      .type("www.techsolutions.com");
    cy.contains("label", "Sector").next("input").type("Tecnología");
    cy.contains("label", "País").next("input").type("España");

    cy.get('button[type="submit"]').click({ force: true });

    cy.get(".toast", { timeout: 10000 })
      .should("be.visible")
      .and("contain.text", "Empresa creada correctamente");
  });

  it("Debería filtrar empresas por nombre", () => {
    mount(ListadoEmpresa);
    cy.wait("@getEmpresas");
    cy.get('input[placeholder="Filtrar por nombre"]').type("TechSolutions");
    cy.get("table tbody tr")
      .should("have.length", 1)
      .and("contain.text", "TechSolutions");
  });

  it("Debería resetear los filtros", () => {
    mount(ListadoEmpresa);
    cy.wait("@getEmpresas");
    cy.get('input[placeholder="Filtrar por nombre"]').type("TechSolutions");
    cy.get("table tbody tr")
      .should("have.length", 1)
      .and("contain.text", "TechSolutions");
    cy.get("button").contains("Resetear Filtros").click({ force: true });
    cy.get("table tbody tr").should("have.length", 2);
  });

  it("Debería ordenar empresas alfabéticamente", () => {
    mount(ListadoEmpresa);
    cy.wait("@getEmpresas");
    cy.get("button").contains("Ordenar Alfabéticamente").click({ force: true });
    cy.get("table tbody tr").first().should("contain.text", "AI Innovators");
  });
});
