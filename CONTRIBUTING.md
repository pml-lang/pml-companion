# How to Contribute

## Table Of Contents

[Overview](#overview)

[Resources](#resources)

[Contributing](#contributing)
- [How to report a bug](#bug-report)
- [How to submit an enhancement?](#submit-enhancement)
- [How to ask a question?](#ask-question)
- [Git branches](#git-branches)
- [Pull requests](#pull-requests)
- [How to submit a PR](#submit-pr)


## Overview

We use Github to:
- host source code
- track issues
- discuss ideas, new features, etc.
- answer questions
- manage pull requests (PRs)

Everybody is welcome to participate in a friendly, collaborative environment.

Your input counts and is very much appreciated!

## Resources

[PML website]

[PML documentation]

[install the _PML Companion_]

[PML Changelog]

## Contributing

### How to report a bug

To report a bug open a new [issue].

A bug report should include:
- a descriptive title
- a clear description of the bug (e.g. expected vs actual behavior)
- the simplest possible sample code that allows to reproduce the bug
- the application's version number
- your OS name and version
- optional:
    - suggestion(s) to fix the bug
    - any other information that helps to fix the bug

### How to submit an enhancement?

Open a new [discussion] to submit an idea, new feature, or any other enhancement.

### How to ask a question?

Open a new [discussion] and label it as `question` or `help wanted`.

### Git branches

The following Git branches are used:

- `main`

    This branch contains the source code of the latest public, stable release.
    
    `main` is the default branch. It is updated each time a new release (new version) is published.

- `develop`

    This branch contains the source code of the next release that will be published in the future (i.e. the upcoming release under construction).

    When a new release is published, branch `develop` is merged into `main`, using `rebase`.

- `release-x.x.x`

    Each time a new version is released, the source code is saved into a branch named `release-x.x.x`, where `x.x.x` is the version number (e.g. release-3.2.1).

    These branches are never updated (unless there is an important hotfix to apply). They represent snapshots of past versions.

### Pull requests

Pull requests (PRs) should normally be submitted to branch `develop`.

The only exception is a PR for a hotfix (a bug that needs to be fixed quickly in the latest public version).
Hotfix PRs should be submitted to branch `main`. If accepted, they will be merged into `main` and `develop`.

By submitting a PR, you agree that your submission is governed by the license used for the repository.

### How to submit a PR

For detailed instructions please search the net for 'submit pr to github' or read [How to create a pull request in GitHub](https://opensource.com/article/19/7/create-pull-request-github).

Here is a summary of the steps involved:

- Fork the repository to your Github account.
- Clone the forked repository to your local system.
- Create your local branch from branch `develop` (or from `main` in case of a hotfix).
- Make your changes and test them very well.
- Commit and push your changes to your forked repo.
- Issue a PR and provide all relevant information for maintainers.
- Be ready to
    - answer questions that maintainers might have
    - improve your PR if needed.
- Congratulate yourself!


<!-----------------------------------------------------------------------------
                               REFERENCE LINKS
------------------------------------------------------------------------------>

[PML website]: https://www.pml-lang.dev "Visit the PML website"
[PML documentation]: https://www.pml-lang.dev/docs/index.html "Visit the PML's documentation page"
[Install the _PML Companion_]: https://www.pml-lang.dev/downloads/install.html "Go to PMLC download page"
[PML Changelog]: https://www.pml-lang.dev/docs/changelog/index.html "View the online PML/PMLC Changelog"

[issue]: https://github.com/pml-lang/pml-companion/issues "Go to the Github issues page"
[discussion]: https://github.com/pml-lang/pml-companion/discussions "Go to the Github discussions page"
