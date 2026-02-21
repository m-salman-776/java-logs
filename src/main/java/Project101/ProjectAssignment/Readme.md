1.The system should be able to onboard new Leads and Developers.
2.Leads should be able to post projects for developers. Each project will have a predefined category (e.g., Frontend, Backend, DevOps, etc.), and the list of categories is fixed.
3.Developers should be able to view available projects and request to work on a project. A developer can only work on any one project at a time.
4.Leads can receive multiple requests from the developers but can approve only one request per project.
5.Once a request is approved, the project is assigned to that developer.
6.Leads can cancel a project before it is approved. If a project is canceled, all pending requests should be removed.
7.Once assigned, the developer can start working on the project and later mark it as completed.
8.The system should track the status of projects and developers (e.g., Open, Requested, Assigned, In Progress, Completed).
9.Once a developer starts a project, the Lead cannot cancel it.
10.If no developer requests a project within a certain period of its creation, it should be automatically canceled. For demo purposes, we can assume some small duration.
11.Ensure the system is thread-safe and handles all concurrency scenarios properly.
