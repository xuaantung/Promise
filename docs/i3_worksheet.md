What technical debt has been cleaned up
========================================

Show links to a commit where you paid off technical debt. Write 2-5 sentences
that explain what debt was paid, and what its classification is.

In early development, we used "System.out.println()" for debugging convenience. But the project is running on the emulator now, it becomes difficult to locate the error. In this iteration, we replace it with Log.i(). This way we can locate errors directly in logcat.
[commit paid off technical debt](https://code.cs.umanitoba.ca/winter-2022-a01/group-6/promise/-/merge_requests/84/diffs?commit_id=5d8629471e582e1e00df7b154e78114a7ded2d38)
What technical debt did you leave?
==================================
One example of something I would really like to fix is the NotifService class. NotifService was originally designed and tested without dependency injection in mind. It's also a class that relies a lot on other business layers. Because of this, the class could not be tested with a fake database. There was an attempt to refactor the code with dependency injection but tests failed because of how much it's coupled to other classes. Passing in a fake database of notifications doesn't account for the fact that NotifService calls the AccountService class to process notifications and AccountService also doesn't have dependency injection and relies completely on the real database. Of course these fake notifications will trigger exceptions for not existing because the accounts they refer to don't actually exist. Going through and refactoring AccountService to use dependency injection also hit a wall because it relies on UserService which also doesn't have dependency injection and thus completely relies on the real database. Injecting a fake database of accounts into AccountService would also lead to exceptions being thrown as these are fake accounts that aren't associated to any real user. Eventually, we saw this as a lot of changes to some of the most integral parts of our code and since so many other things were being added and in such a short amount of time, we didn't want to risk breaking our code so that we could write more unit tests.

I would classify this debt as inadvertent and reckless. It was inadvertent because when we switched over from using our fake database to a real one, we didn't realize that we would need to keep the fake databases for testing so we didn't know that this would end up being such an issue later on. However, it was also reckless because dependency injection had been mentioned many times in class so this was knowledge that was available to us. Even though we didn't need it at the time, we could have coded our classes with dependency injection in mind so that these issues could be avoided in the future. In fact, some of our classes, like TaskService, did have dependency injection added in shortly after the switch to using the real database. For those classes, testing with the fake database was not an issue.

Discuss a Feature or User Story that was cut/re-prioritized
============================================
[Request Access to a Project](https://code.cs.umanitoba.ca/winter-2022-a01/group-6/promise/-/issues/13) was a user story that ended up being cut. The way our project works is that users must log in to access our features and once they do, they only have access to projects they've created or been invited to. The code for the logic of requesting access to a project is pretty simple and is basically an extension of the code for "Send Project Invites" but when thinking about the UI design for this user story, we realized that it didn't make sense to have a "request access" button with the way our app is set up right now. The idea was to have a "request access" button that was only visible to projects someone wasn't a part of and a "send invites" button that would be visible only on projects someone did have access to. But then the realization came that this "request access" button would never be visible because right now there is no way for a user to see a project they don't already have access to. In order to implement this, we would need additional features like the ability to change the visibility of a project (options for a project to be visible to everyone or only to certain people) and the ability to search for projects. This would add more time that we did not have on features we didn't originally prioritize so we thought it was better to cut the user story.

Acceptance test/end-to-end
==========================
One of the tests we had was a test on the [Members](https://code.cs.umanitoba.ca/winter-2022-a01/group-6/promise/-/blob/iteration3/app/src/androidTest/java/comp3350/group6/promise/presentation/MemberTest.java) feature.
This feature required a long test because it covered almost all aspects of our application (accounts, projects, invitations) and we had to do multiple logins & logouts within a single test.
To make the test more isolated and avoid dependency issues, we created all the objects within the test rather than using default values in the database.

The flow of the test is as follows,

First, it creates three accounts (123, 456, and 789) to test on.

Then, it would create a project in 789's account and invite 123 and 456.

123 and 456 accepts the invitation, and will verify that they are a MEMBER to that project.

789 then gives 123 and ADMIN role, and 123 will verify that now it is an ADMIN of that project.

Acceptance test, untestable
===============
We had a hard time setting up the tests because we had some Android dependencies that would prevent us from using the Espresso framework. 
We did not have a lot of challenges with flaky acceptance tests because our application did not have non-deterministic components.
However, there were some elements that we were not able to `.check()` because of the way we constructed our UI. 
They were only testable by running the app, and not with automated assertions.

Velocity/teamwork
=================
Our estimations did not improve much through out the course.

[Group Velocity chart](../docs/images/group-velocity.png)

For the first iteration, we [underestimated](https://code.cs.umanitoba.ca/winter-2022-a01/group-6/promise/-/issues/36) the time that would take for each task. 
There were a lot of learnings to do on Android specific elements for the presentations layer and for the database.

In the second iteration, we had a mixture of [underestimation](https://code.cs.umanitoba.ca/winter-2022-a01/group-6/promise/-/issues/28) and [overestimation](https://code.cs.umanitoba.ca/winter-2022-a01/group-6/promise/-/issues/49).
The under/overestimation here was mostly on the UI layers, where the members who had more UI related code in the first iteration, tended to have an overestimation, and the members who had less code with UI components tended to underestimate the time.
Because we learned a lot from the first iteration and setup most of the things, we expected that the second iteration would be much smoother.
However, adding the real database and tests took us quite some time, resulting in an overall underestimation of the expected time.

For the third iteration, overall we [overestimated](https://code.cs.umanitoba.ca/winter-2022-a01/group-6/promise/-/milestones/4#tab-issues) the time. However, individual tasks had a much closer estimation compared to previous iterations; only having a maximum margin of 6 hours.
