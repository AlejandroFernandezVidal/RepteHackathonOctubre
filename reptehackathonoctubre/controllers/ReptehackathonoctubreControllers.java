package cat.itacademy.barcelonactiva.fernandezvidal.alejandro.reptehackathonoctubre.controllers;

import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReptehackathonoctubreControllers {
	@GetMapping("/convertir")
	public String convertirCsvAJson() throws IOException {
		String json = "";
		String path = "/reptehreptehackathonoctubre/controllers/archivo.csv";
		List<String> csvColumnas = null;
		try (var lectura = Files.lines(Paths.get(path))) {
			csvColumnas = lectura.collect(Collectors.toList());

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (csvColumnas != null) {
			json = csvJson(csvColumnas);
		}

		return json;
	}

	public String csvJson(List<String> csv) {
		csv.removeIf(e -> e.trim().isEmpty());
		if (csv.size() <= 1) {
			return "[]";
		}
		String[] columnas = csv.get(0).split(",");

		StringBuilder json = new StringBuilder("[\n");
		csv.subList(1, csv.size()).stream().map(e -> e.split(",")).filter(e -> e.length == columnas.length)
				.forEach(fila -> {
					json.append("\t{\n");
					for (int i = 0; i < columnas.length; i++) {
						json.append("\t\t\"").append(columnas[i]).append("\" : \"").append(fila[i]).append("\",\n");
					}
					json.replace(json.lastIndexOf(","), json.length(), "\n");
					json.append("\t},");
				});
		json.replace(json.lastIndexOf(","), json.length(), "");
		json.append("\n]");
		return json.toString();
	}

}
