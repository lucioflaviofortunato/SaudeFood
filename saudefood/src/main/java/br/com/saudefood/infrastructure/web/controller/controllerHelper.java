package br.com.saudefood.infrastructure.web.controller;

import org.springframework.ui.Model;

public class controllerHelper {

	public static void setEditMode(Model model, boolean isEdit) {
		model.addAttribute("editMode", isEdit);
	}
}
