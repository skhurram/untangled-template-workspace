Tasks:

- Make our untangled server config usable from Java
- Figure out connection pooling and database injection 
   - For production code
   - Augment with a story for creating/starting/injecting databases for tests
- Database seeding
   - Production evolution (static one-time import)
   - Test seeding with tempid map for use in the test
       - As simple as possible, but with:
           - Support for column default values (so you can seed just the columns you care about)
               - Allows the creation of a new row in any table with JUST the ID (all other columns are always optional)
               - E.g. a central config file that says what the default values are for tables/columns
               - Localized overrides?
           - Support for generative keywords (e.g. anyInt, anyDouble, anyTime...or perhaps schema detection so you can just say 'any')
           - Support for exact and relative time input (e.g. timestamp("2012-01-04 11:35:00"), date(today, +1 month), timestamp(now, -5 minutes))
           - Support for symbolic IDs
           - Support for includes (composition)

Viewpoints:

- Developer (park bench)
- Deploy
- Test
- CI

Ideas:

Sample Migration:

```
-- Author: Joe Schmoe
-- Date: 2016-11-05
-- Approved-by: Sam Reviewer

create table boo (
   id serial primary key not null,
   thing text not null,
   last_modified timestamp not null default current_timestamp
   );
```

Sample Seeding

Row Defaults for Seeding (boo-defaults):

```
boo(id=any, thing=any, last_modified=time(now, -1 minute))
```

Sample seed file for a test (boo-seed):

```
boo(id=MyBoo, thing="This is a test")
```

Sample usage:

```
map = seed(database, "boo-defaults", "boo-seed")
long rowID = map.get("MyBoo") 
```
