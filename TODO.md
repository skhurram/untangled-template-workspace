Tasks:

- Figure out where schema lives
- Defaults
    - Make sure strings are case-sensitive
    - UTF-8
    - Numerics for money
    - UTC time zone. Careful about how date are translated from client
    - Bigserial PKs
    - Timestamping of rows?
    - FK enforcement, with indexes
    - Cascade delete design
- Figure out how to split/share/version source
- Make our untangled server config usable from Java
- Figure out connection pooling and database injection 
   - For production code
   - Connection lifecycle
   - Augment with a story for creating/starting/injecting databases for tests (NO PII)
       - Using template for speed
- Include read streaming replication from the start
- Security
   - PII tokenization and such
   - Remove private data associated with a person
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

Sample Seeding (use EDN???)

Row Defaults for Seeding (boo-defaults):

```
{
 TABLE_NAME { COL VAL COL VAL COL VAL }
 boo { id any, thing any, last_modified (now -1 minute))
}
```

Sample seed file for a test (boo-seed): (EDN? YAML?)

```
[TABLE_NAME { COL VAL COL VAL } 
 boo { id MyBoo, thing "This is a test" }
 other {id MyOther, parentBooFK MyBoo }]
```

Sample usage:

```
map = seed(database, "boo-defaults", "boo-seed", additionalSeedFilenames)
long rowID = map.get("MyBoo") 
```
