CREATE TABLE [dbo].[Readerinformation](
 [Readerid] [bigint] NOT NULL,
 [Readername] [varchar](10) NOT NULL,
 [Readerage] [int] NULL,
 [Readertel] [bigint] NOT NULL,
 [Password] [varchar](10) NOT NULL,
 [ReaderTpye] [varchar](10) NOT NULL,
 CONSTRAINT [PK_Readerinformation] PRIMARY KEY CLUSTERED 
(
 [Readerid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
