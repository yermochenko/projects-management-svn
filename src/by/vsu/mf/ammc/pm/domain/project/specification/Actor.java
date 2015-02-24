package by.vsu.mf.ammc.pm.domain.project.specification;

import java.util.ArrayList;
import java.util.List;

import by.vsu.mf.ammc.pm.domain.NamedEntity;
import by.vsu.mf.ammc.pm.domain.project.Project;

public class Actor extends NamedEntity {
	private Project project;
	private List<Actor> parents = new ArrayList<>();
	private List<UseCase> useCases = new ArrayList<>();

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Actor> getParents() {
		return parents;
	}

	public List<UseCase> getUseCases() {
		return useCases;
	}
}
