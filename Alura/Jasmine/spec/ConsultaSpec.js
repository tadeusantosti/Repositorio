describe("Consulta", function() {

 var guilherme;

    beforeEach(function() {
        guilherme = new Paciente("Guilherme", 28, 72, 1.80);
    });
  
 describe("Consultas que sao retornos", function() {
	  it("Nao deve cobrar nada", function() {
		var consulta = new Consulta(guilherme, [], true, true);

	        expect(consulta.preco()).toEqual(0);
	    });
  });

describe("Consultas que sao simples", function() {
  it("Deve cobrar 25 por cada", function() {
	var consulta = new Consulta(guilherme, ["proc1", "proc2", "proc3"], false, false);

        expect(consulta.preco()).toEqual(75);
    });
});

describe("Consultas que sao particulares", function() {
	  it("Deve dobrar o valor exames comuns", function() {
		var consulta = new Consulta(guilherme, ["proc1", "proc2", "proc3"], true, false);

	        expect(consulta.preco()).toEqual(150);
    	});

	  it("Deve dobrar o valor exames especificos", function() {
		var consulta = new Consulta(guilherme, ["raio-x", "gesso"], true, false);

	        expect(consulta.preco()).toEqual(174);
    	});

    });
});