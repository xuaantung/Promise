# Promise
> PROject Management Is So Easy with Promise!

Promise is an Android application for students or software development companies who wish to efficiently manage projects and human resources. Instead of having multiple platforms for project management, our system provides an all-in-one space for organization, collaboration, and communication.

### Organization
The primary functionality of the system is task management. Users will be able to add tasks to a project and assign members to each task. Tasks will have a title, a description of what is needed to be done, and a priority. The tasks will be divided and organized by their progress. Newly created tasks, tasks in progress, complete tasks to be reviewed, and tasks that are reviewed will be separated for the project members to easily manage them.

### Collaboration
The tasks are strongly associated with release plans. Creating a release plan with the system will be an iterative process. A release plan will have an initial due date, and users can add tasks to that plan. When adding the tasks to a release plan, a member is assigned to a task, and the system will ask that member to estimate the cost(time) for the task that was given to them. The users can review the release plan according to the time limit they have, and either confirm or adjust the release plan. Teams will be able to adjust the start time and estimated completed time of development according to their own ability and task difficulty.

The system will allow users to create or join a project that was created by some other user. To ensure security, users can have roles like project manager, client, team member, etc. and each role will have different levels of access. For instance, a flat organization like a student group may only have team members with equal access to the project. On the contrary, for a project that has clients and developers, the clients will be able to create a task, but will not be assigned to one.

### Communication
The system will also serve as a communication platform. Users can share their schedules and will have access to a calendar that shows the schedules of other users within a project. This will help the users distribute work flexibly, review their own schedule and organize group meetings that work for all members. Sending messages to other members will be available to eliminate the need for other messaging apps.


### Success criteria
The project will be considered successful on the following criteria: First, users of existing project management apps prefer our system over the system they have been using. Second, new users find it pleasing and intuitive and choose to use our system after a trial. Third, it reduces the time and space needed for actual project management.

## Branching Strategy
Our group will follow a simplified version of the git-flow strategy. We will have the main branch, release branches for each iteration, and feature branches where each member will develop their user stories individually. Before merging into the main branch, each member will merge their feature branches to the corresponding release branch for testing and conflict resolution. Finally, when the release branch is functional with all release plans satisfied, we merge it into the main branch. Direct commits to the main branch will only be hotfixes. This branching strategy will allow members to easily merge into one branch with less conflicts and keep the main branch with fully functional at all times.

# Links to Documents
* [Iteration 1 Worksheet](./docs/i1_worksheet.md)
* [Iteration 2 Worksheet](./docs/i2_worksheet.md)
* [Iteration 3 Worksheet](./docs/i3_worksheet.md)
* [Project Architecture](./docs/ARCHITECTURE.md)
