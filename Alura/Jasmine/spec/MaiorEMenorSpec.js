describe("Maior e Menor", function() {

    it("deve entender numeros em ordem nao sequencial", function() {
        var algoritmo = new MaiorEMenor();
        algoritmo.encontra([5,15,7,9]);

        expect(algoritmo.pegaMaior()).toEqual(15);
        expect(algoritmo.pegaMenor()).toEqual(5);
    });


    it("deve entender numeros em ordem decrescente", function() {
        var algoritmo = new MaiorEMenor();
        algoritmo.encontra([15,11,9,7]);

        expect(algoritmo.pegaMaior()).toEqual(15);
        expect(algoritmo.pegaMenor()).toEqual(7);
    });

   it("deve entender numeros em ordem crescente", function() {
        var algoritmo = new MaiorEMenor();
        algoritmo.encontra([5,7,9,12]);

        expect(algoritmo.pegaMaior()).toEqual(12);
        expect(algoritmo.pegaMenor()).toEqual(5);
    });

    it("deve entender numeros de mesmo valor", function() {
        var algoritmo = new MaiorEMenor();
        algoritmo.encontra([9]);

        expect(algoritmo.pegaMaior()).toEqual(9);
        expect(algoritmo.pegaMenor()).toEqual(9);
    });
});