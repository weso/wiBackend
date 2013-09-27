			$(function() {
				var p = new Params();

				p.labels = ["Indicador 1", "Indicador 2"];
				p.options.margins = [10, 20, 40, 30];

				p.options.min = null;
				p.options.max = null;
				p.regions[0] = new Region("Pola de Siero", [70, 90, 80, 95]);
				p.regions[1] = new Region("Oviedo", [30, 50, 40, 5]);


				p.container = "#nvd3-line";
				new D3Connector().drawLineChart(p);
			});