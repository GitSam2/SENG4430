# Changelog

## [1.3.0](https://github.com/GitSam2/SENG4430/compare/v1.2.0...v1.3.0) (2026-04-24)


### Features

* add dependency to self test. Fix pom to not include dependency … ([6b17d45](https://github.com/GitSam2/SENG4430/commit/6b17d45d08eb03ef2fd59b452afc1a8047e32458))
* add dependency to self test. Fix pom to not include dependency with potential CVE, testng, removed outdated references to JUnit 4, passed self test ([d480a03](https://github.com/GitSam2/SENG4430/commit/d480a038a984565cbe0167634cc35b7e37e46ef3))

## [1.2.0](https://github.com/GitSam2/SENG4430/compare/v1.1.0...v1.2.0) (2026-04-20)


### Features

* add bash and batch scripts to launch qualitytool ([8da076f](https://github.com/GitSam2/SENG4430/commit/8da076f71fa3dae0a9491546a70a62ab424566d8))
* add dependency module resolving, parent resolving and counting will throbbing ([c5a1bc2](https://github.com/GitSam2/SENG4430/commit/c5a1bc264eb4bf403f65a799efca261836ae3a96))
* made metricCommand a callable int so error codes propagate upwards ([439083f](https://github.com/GitSam2/SENG4430/commit/439083ffd0bfc084b7c9252e6234c4675f57315e))
* refactored commands ([fafd392](https://github.com/GitSam2/SENG4430/commit/fafd3922910c69bbedabac0cf029ae8e45f88493))
* refactored commands ([d23b8de](https://github.com/GitSam2/SENG4430/commit/d23b8de37206787ca815f46d948b9dfededd15ba))


### Bug Fixes

* added caching to DIT algorithm for improved mem management ([ceb50fd](https://github.com/GitSam2/SENG4430/commit/ceb50fd10c390f00a5b25544f932594bebb66f89))
* added caching to DIT algorithm for improved mem management ([70629f7](https://github.com/GitSam2/SENG4430/commit/70629f73c3e4f2f6b1a142ad0652e99998eb8352))
* adjusted factory implementation to support easy modification of … ([f7a48fb](https://github.com/GitSam2/SENG4430/commit/f7a48fb479f0283c902ba4aba724fb443508e480))
* adjusted factory implementation to support easy modification of parameters ([5305a88](https://github.com/GitSam2/SENG4430/commit/5305a88f5d9b62064dfab09ca4668a74a77dd003))
* adjusted output to use table ([3077f8f](https://github.com/GitSam2/SENG4430/commit/3077f8fffc3891636773094c2604d61c195fa859))
* adjusted table ([3e65782](https://github.com/GitSam2/SENG4430/commit/3e657826ca96e2f5f80bdf28c42c5d343f9b72b5))
* adjusted table that was hidding class names when running the dit metric ([e517827](https://github.com/GitSam2/SENG4430/commit/e5178271ef1d10c3b018b5e1ff4fafa36956b0fd))
* fix no-colour flag working opposite ([07ad597](https://github.com/GitSam2/SENG4430/commit/07ad59740fb3419bbdc6e1974456ba37da66d0b4))
* fixed logic error that caused geometric usage of memory ([7508ba0](https://github.com/GitSam2/SENG4430/commit/7508ba09651cfdb16f28f5ba767697c6e032ef45))
* improved comments ([a6fe928](https://github.com/GitSam2/SENG4430/commit/a6fe928841ed8da585007d59f538dce85836a3a5))
* improved comments ([f8addef](https://github.com/GitSam2/SENG4430/commit/f8addeff485cb27aab35ea36abae8e782bb2a3b6))
* refactoring regressed the throbber functionality. Added a temporary description section to dependency ([a3525f5](https://github.com/GitSam2/SENG4430/commit/a3525f5320a0d2f1ee1afa3102b8a06e594b810c))
* tests had small warnings and errors that stopped compilation ([d31920b](https://github.com/GitSam2/SENG4430/commit/d31920b8567825d1f97ae29f06520d1f19762bdd))
* used record for better practice ([d1750ee](https://github.com/GitSam2/SENG4430/commit/d1750ee469f98fd143ec4d58fe692994150005ec))
* used record for better practice ([3eea30e](https://github.com/GitSam2/SENG4430/commit/3eea30e89a9e81624cb2b32caa28f951d393918b))

## [1.1.0](https://github.com/GitSam2/SENG4430/compare/v1.0.0...v1.1.0) (2026-03-26)


### Features

* add basic dependency gathering classes ([c247f34](https://github.com/GitSam2/SENG4430/commit/c247f34c54c804a44ff69d71c56d43942cf77635))
* add basic dependency gathering classes ([f22450a](https://github.com/GitSam2/SENG4430/commit/f22450a30d7ff12ea770246f1e780128f789517b))
* add batching of cve info gathering ([1fb0daa](https://github.com/GitSam2/SENG4430/commit/1fb0daa5dc2c469e9d97ff813603e3f907fb8c07))
* add collection of cves from dependency id, group and version ([5861c71](https://github.com/GitSam2/SENG4430/commit/5861c7109fc34787187e4418e7bdd574089489ce))
* add Dependency Metric ([a02bece](https://github.com/GitSam2/SENG4430/commit/a02beceab5ac14e05bad96080d591158443416f7))
* add LoopDepthCommand ([6a0fe42](https://github.com/GitSam2/SENG4430/commit/6a0fe42d62428c0d7d2abce176b5b7583290ce10))
* add maven test and test report to push and pull request on main ([349ff54](https://github.com/GitSam2/SENG4430/commit/349ff543869f6e81b45fb005004aea818b872b35))
* add model object for final printing of dependencies ([21924d4](https://github.com/GitSam2/SENG4430/commit/21924d4942651b42ece2657f54f189b92330ec68))
* add service to get latest version of a dependency ([374d593](https://github.com/GitSam2/SENG4430/commit/374d5935e8855366dd0e5c60c8c1ee223ae55ff8))
* add wmc metric and command ([29074f8](https://github.com/GitSam2/SENG4430/commit/29074f8aa7eeead31b01750042f921c79bdcc38f))
* added a basic dependency tree print utility to test dependency gathering ([158359d](https://github.com/GitSam2/SENG4430/commit/158359d30909de6c6054b13fa725e52ef6899fb8))
* added command for dit to cli ([e7472cc](https://github.com/GitSam2/SENG4430/commit/e7472ccb8c1a5a3eee53b8675f8cd1d292df04cd))
* added one fully featured and stub test ([811ed96](https://github.com/GitSam2/SENG4430/commit/811ed96ddd65fdaae4fddc841d7418f24ace529e))
* added release please for codelines ([0da97ce](https://github.com/GitSam2/SENG4430/commit/0da97ce07f94567319214efc7fcc5843a4e61822))
* added release please for codelines ([c6c3f6d](https://github.com/GitSam2/SENG4430/commit/c6c3f6d532311da655579c53452e404776cad36a))
* cc cyclomaticComplexity metric added ([09546c9](https://github.com/GitSam2/SENG4430/commit/09546c9f8e91f1adf874d654d2c0f9dcf3f6a140))
* commands now run in sequence and all subcommands run ([66e6fbc](https://github.com/GitSam2/SENG4430/commit/66e6fbcb9bd313c1d24ee290c1d1db7082d36cab))
* Create a suite of picocli commands ([5bcf321](https://github.com/GitSam2/SENG4430/commit/5bcf321220c42fa309c499ba10f2660535cc536e))
* create new dependency metric tests ([573ac95](https://github.com/GitSam2/SENG4430/commit/573ac958b246ddd607448c2591b26e5567175bd3))
* include identifier length metric ([c75e7ae](https://github.com/GitSam2/SENG4430/commit/c75e7ae7bfc7f2c653d397c63c6310f1c15040e7))


### Bug Fixes

* add protection on file parse error ([8112ed8](https://github.com/GitSam2/SENG4430/commit/8112ed84cb64097ea57b8d1a22a8f85e6df63447))
* added more unit tests for dit ([b1dd1d0](https://github.com/GitSam2/SENG4430/commit/b1dd1d0878237237d9ac3533950460e108a9b059))
* added more unit tests for dit ([dc1f4bd](https://github.com/GitSam2/SENG4430/commit/dc1f4bd54339ee0eaadd3276c4448e33707c9873))
* added output for metric result and interface to keep output functions consistent ([e91d467](https://github.com/GitSam2/SENG4430/commit/e91d467fbb596fdd9d04bbc675896a75674b2c0e))
* added test for multiple classes ([5446656](https://github.com/GitSam2/SENG4430/commit/5446656acdb820408001acfac1aa7563c6bcf6c0))
* changed name of release please action and adjust step dependencies ([21a869f](https://github.com/GitSam2/SENG4430/commit/21a869fabfc35530a56f020270ba1c37a4852dcf))
* changed name of release please action and adjust step dependencies ([792d6df](https://github.com/GitSam2/SENG4430/commit/792d6df9cc24cb28a39fbf03cc8f3657b3902d44))
* complying with the interfaces for metric ([655c980](https://github.com/GitSam2/SENG4430/commit/655c980c74b97f3440c402f874a785a12ece9fe4))
* file structure for rebase ([bb39136](https://github.com/GitSam2/SENG4430/commit/bb3913658d28fe0ed124514f6fe521695384b24b))
* fixed duplicate tags in pom.xml after merge ([e46a754](https://github.com/GitSam2/SENG4430/commit/e46a75418f6e69c42ad969bec61cbbc695582cdd))
* fixed id length test ([9f3ac6f](https://github.com/GitSam2/SENG4430/commit/9f3ac6fc316e73dfb17b1579d5636710ed1a29da))
* qualityCLI now has working fail threshold option and initialises javaparser for subcommands ([32bc9f2](https://github.com/GitSam2/SENG4430/commit/32bc9f2583c91194ef40cf8f612ba1e34697cadf))
* remove old imports in main ([3dc3533](https://github.com/GitSam2/SENG4430/commit/3dc3533f0b131d778d7b8a2a6e537947421f854b))
* removed test case that wasn't needed ([8e2ee11](https://github.com/GitSam2/SENG4430/commit/8e2ee11db43466caf7f01d164d2707271cc2fffb))
* resolved issues with uni tests ([8a7f015](https://github.com/GitSam2/SENG4430/commit/8a7f01538f177a0c7afa7f5fbe9d835bf110d937))
* single class metric dit working ([716539c](https://github.com/GitSam2/SENG4430/commit/716539cfe657d12dc0aa3d31fca13aa0131260b3))
* table printing is now fixed ins Console ([f81abe6](https://github.com/GitSam2/SENG4430/commit/f81abe65b358fa6eac087e8dcb56ee2be3ccefb7))
* Update pom.xml to include json capabilities ([9d35db3](https://github.com/GitSam2/SENG4430/commit/9d35db3cc554c1612cb0b054f89e48e7ae9336b7))
* use undeprecated repositorysystemsupplier over defaultservicelocator ([599f17c](https://github.com/GitSam2/SENG4430/commit/599f17c96dc2be8ef2d78d67f816eba2496f3ca7))
