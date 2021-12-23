CREATE TABLE [dbo].[borrowinformation](
 [Bookid] [varchar](20) NOT NULL,
 [Readerid] [bigint] NOT NULL,
 [Borrownumber] [int] NOT NULL,
 [Borrowtime] [smalldatetime] NOT NULL,
 [Returntime] [smalldatetime] NULL
) ON [PRIMARY]
