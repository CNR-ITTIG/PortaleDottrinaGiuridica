
/*  A Bison parser, made from css_syntax.y
    by GNU Bison version 1.28  */

#define YYBISON 1  /* Identify Bison output.  */

#define	IMPORT_SYM	257
#define	IMPORTANT_SYM	258
#define	IDENT	259
#define	STRING	260
#define	NUMBER	261
#define	PERCENTAGE	262
#define	LENGTH	263
#define	EMS	264
#define	EXS	265
#define	LINK_PSCLASS_AFTER_IDENT	266
#define	VISITED_PSCLASS_AFTER_IDENT	267
#define	ACTIVE_PSCLASS_AFTER_IDENT	268
#define	FIRST_LINE_AFTER_IDENT	269
#define	FIRST_LETTER_AFTER_IDENT	270
#define	HASH_AFTER_IDENT	271
#define	CLASS_AFTER_IDENT	272
#define	LINK_PSCLASS	273
#define	VISITED_PSCLASS	274
#define	ACTIVE_PSCLASS	275
#define	FIRST_LINE	276
#define	FIRST_LETTER	277
#define	HASH	278
#define	CLASS	279
#define	URL	280
#define	RGB	281
#define	CDO	282
#define	CDC	283

#line 1 "css_syntax.y"

#include <stdio.h>
#include <string.h>
#include "css_lex.h"
#include "parser.h"

#define YYPARSE_PARAM yyparam
#define YYERROR_VERBOSE 1
//#define YYDEBUG 1


#line 15 "css_syntax.y"
typedef union {
	char *lexeme;
	char letter;
	struct property_t *property;
	struct selector_t *selector;
	struct selector_list_t *selector_list;
	int pseudo_class;
	int pseudo_element;
} YYSTYPE;
#include <stdio.h>

#ifndef __cplusplus
#ifndef __STDC__
#define const
#endif
#endif



#define	YYFINAL		97
#define	YYFLAG		-32768
#define	YYNTBASE	38

#define YYTRANSLATE(x) ((unsigned)(x) <= 283 ? yytranslate[x] : 69)

static const char yytranslate[] = {     0,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,    32,    34,    31,     2,    33,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,    37,    30,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,    35,     2,    36,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
     2,     2,     2,     2,     2,     1,     3,     4,     5,     6,
     7,     8,     9,    10,    11,    12,    13,    14,    15,    16,
    17,    18,    19,    20,    21,    22,    23,    24,    25,    26,
    27,    28,    29
};

#if YYDEBUG != 0
static const short yyprhs[] = {     0,
     0,     4,     8,     9,    13,    14,    17,    18,    20,    22,
    26,    28,    30,    32,    34,    36,    38,    39,    41,    46,
    48,    52,    54,    58,    61,    64,    66,    68,    71,    73,
    75,    78,    83,    87,    91,    95,    98,   101,   104,   106,
   110,   113,   116,   118,   121,   123,   125,   127,   129,   131,
   133,   135,   137,   139,   141,   143,   145,   147,   149,   151,
   153,   155,   160,   164,   166,   167,   169,   171,   175,   178,
   180,   182,   184,   186,   188,   190,   192,   194,   196,   198,
   200,   202
};

static const short yyrhs[] = {    41,
    40,    39,     0,    48,    41,    39,     0,     0,    43,    41,
    40,     0,     0,    41,    42,     0,     0,    28,     0,    29,
     0,     3,    44,    30,     0,     6,     0,    26,     0,    31,
     0,    32,     0,    33,     0,    34,     0,     0,     5,     0,
    49,    35,    50,    36,     0,    51,     0,    51,    34,    49,
     0,    63,     0,    63,    30,    50,     0,    52,    59,     0,
    52,    60,     0,    52,     0,    60,     0,    51,     1,     0,
     1,     0,    53,     0,    53,    52,     0,    54,    61,    57,
    55,     0,    54,    61,    55,     0,    54,    57,    55,     0,
    54,    61,    57,     0,    54,    61,     0,    54,    57,     0,
    54,    55,     0,    54,     0,    62,    57,    55,     0,    62,
    57,     0,    62,    55,     0,    62,     0,    58,    55,     0,
    58,     0,    56,     0,     5,     0,    12,     0,    13,     0,
    14,     0,    19,     0,    20,     0,    21,     0,    18,     0,
    25,     0,    16,     0,    15,     0,    23,     0,    22,     0,
    17,     0,    24,     0,    47,    37,    65,    64,     0,    47,
    37,    65,     0,     1,     0,     0,     4,     0,    66,     0,
    65,    46,    66,     0,    45,    67,     0,    67,     0,     7,
     0,     6,     0,     8,     0,     9,     0,    10,     0,    11,
     0,     5,     0,    68,     0,    26,     0,    27,     0,    24,
     0,    17,     0
};

#endif

#if YYDEBUG != 0
static const short yyrline[] = { 0,
    96,   102,   114,   118,   119,   123,   124,   128,   129,   133,
   137,   138,   162,   163,   167,   168,   169,   173,   177,   193,
   203,   218,   221,   232,   240,   248,   249,   250,   251,   255,
   256,   269,   279,   289,   299,   309,   319,   329,   339,   349,
   359,   369,   379,   389,   399,   409,   422,   426,   427,   428,
   432,   433,   434,   438,   442,   446,   447,   451,   461,   478,
   482,   486,   495,   504,   505,   511,   515,   516,   529,   537,
   541,   542,   543,   544,   545,   546,   547,   548,   549,   550,
   558,   563
};
#endif


#if YYDEBUG != 0 || defined (YYERROR_VERBOSE)

static const char * const yytname[] = {   "$","error","$undefined.","IMPORT_SYM",
"IMPORTANT_SYM","IDENT","STRING","NUMBER","PERCENTAGE","LENGTH","EMS","EXS",
"LINK_PSCLASS_AFTER_IDENT","VISITED_PSCLASS_AFTER_IDENT","ACTIVE_PSCLASS_AFTER_IDENT",
"FIRST_LINE_AFTER_IDENT","FIRST_LETTER_AFTER_IDENT","HASH_AFTER_IDENT","CLASS_AFTER_IDENT",
"LINK_PSCLASS","VISITED_PSCLASS","ACTIVE_PSCLASS","FIRST_LINE","FIRST_LETTER",
"HASH","CLASS","URL","RGB","CDO","CDC","';'","'-'","'+'","'/'","','","'{'","'}'",
"':'","stylesheet","rulesets","imports","comments","comment","import","string_or_url",
"unary_operator","operator","property","ruleset","selectors","declarations",
"selector","simple_selectors","simple_selector","element_name","pseudo_class",
"solitary_pseudo_class","class","solitary_class","pseudo_element","solitary_pseudo_element",
"id","solitary_id","declaration","prio","expr","term","value","hexcolor", NULL
};
#endif

static const short yyr1[] = {     0,
    38,    39,    39,    40,    40,    41,    41,    42,    42,    43,
    44,    44,    45,    45,    46,    46,    46,    47,    48,    49,
    49,    50,    50,    51,    51,    51,    51,    51,    51,    52,
    52,    53,    53,    53,    53,    53,    53,    53,    53,    53,
    53,    53,    53,    53,    53,    53,    54,    55,    55,    55,
    56,    56,    56,    57,    58,    59,    59,    60,    60,    61,
    62,    63,    63,    63,    63,    64,    65,    65,    66,    66,
    67,    67,    67,    67,    67,    67,    67,    67,    67,    67,
    68,    68
};

static const short yyr2[] = {     0,
     3,     3,     0,     3,     0,     2,     0,     1,     1,     3,
     1,     1,     1,     1,     1,     1,     0,     1,     4,     1,
     3,     1,     3,     2,     2,     1,     1,     2,     1,     1,
     2,     4,     3,     3,     3,     2,     2,     2,     1,     3,
     2,     2,     1,     2,     1,     1,     1,     1,     1,     1,
     1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
     1,     4,     3,     1,     0,     1,     1,     3,     2,     1,
     1,     1,     1,     1,     1,     1,     1,     1,     1,     1,
     1,     1
};

static const short yydefact[] = {     7,
     5,     0,     8,     9,     0,     6,     7,    11,    12,     0,
    29,    47,    51,    52,    53,    59,    58,    61,    55,     1,
     7,     0,     0,    26,    30,    39,    46,    45,    27,    43,
     5,    10,     0,     0,    28,     0,    57,    56,    24,    25,
    31,    48,    49,    50,    60,    54,    38,    37,    36,    44,
    42,    41,     4,     2,    64,    18,     0,     0,    22,    21,
    34,    33,    35,    40,     0,    19,     0,    32,    77,    72,
    71,    73,    74,    75,    76,    82,    81,    79,    80,    13,
    14,     0,    17,    67,    70,    78,    23,    69,    66,    15,
    16,     0,    62,    68,     0,     0,     0
};

static const short yydefgoto[] = {    95,
    20,     5,     1,     6,     7,    10,    82,    92,    57,    21,
    22,    58,    23,    24,    25,    26,    47,    27,    48,    28,
    39,    29,    49,    30,    59,    93,    83,    84,    85,    86
};

static const short yypact[] = {-32768,
     5,    38,-32768,-32768,    66,-32768,-32768,-32768,-32768,   -23,
-32768,-32768,-32768,-32768,-32768,-32768,-32768,-32768,-32768,-32768,
-32768,   -21,     3,    94,    -2,   113,-32768,    -3,-32768,   120,
     5,-32768,    27,     0,-32768,    76,-32768,-32768,-32768,-32768,
-32768,-32768,-32768,-32768,-32768,-32768,-32768,    -3,   120,-32768,
-32768,    -3,-32768,-32768,-32768,-32768,   -25,   -20,    -4,-32768,
-32768,-32768,    -3,-32768,    52,-32768,     0,-32768,-32768,-32768,
-32768,-32768,-32768,-32768,-32768,-32768,-32768,-32768,-32768,-32768,
-32768,    97,     9,-32768,-32768,-32768,-32768,-32768,-32768,-32768,
-32768,    52,-32768,-32768,    31,    40,-32768
};

static const short yypgoto[] = {-32768,
    20,    10,     8,-32768,-32768,-32768,-32768,-32768,-32768,-32768,
    18,     1,-32768,    45,-32768,-32768,   -28,-32768,   -24,-32768,
-32768,    41,-32768,-32768,-32768,-32768,-32768,   -19,   -10,-32768
};


#define	YYLAST		138


static const short yytable[] = {    50,
    55,    51,    12,    35,    56,    52,    32,     2,    42,    43,
    44,    65,    89,    34,    31,    66,    13,    14,    15,    61,
    62,    18,    19,    64,    63,    67,    -3,    11,    33,   -65,
    96,    12,     3,     4,    68,   -65,    36,   -20,   -63,    97,
    53,    90,    91,     8,   -63,    13,    14,    15,    16,    17,
    18,    19,    54,    60,     3,     4,    69,    70,    71,    72,
    73,    74,    75,     9,    40,    -3,    11,    87,    76,    41,
    12,    88,    94,     0,     0,    77,    11,    78,    79,     0,
    12,     0,    80,    81,    13,    14,    15,    16,    17,    18,
    19,     0,     0,     0,    13,    14,    15,    16,    17,    18,
    19,    69,    70,    71,    72,    73,    74,    75,    37,    38,
     0,     0,     0,    76,     0,    16,    17,     0,     0,     0,
    77,     0,    78,    79,    42,    43,    44,     0,     0,    45,
    46,    42,    43,    44,     0,     0,     0,    46
};

static const short yycheck[] = {    28,
     1,    30,     5,     1,     5,    30,    30,     3,    12,    13,
    14,    37,     4,    35,     7,    36,    19,    20,    21,    48,
    49,    24,    25,    52,    49,    30,     0,     1,    21,    30,
     0,     5,    28,    29,    63,    36,    34,    35,    30,     0,
    31,    33,    34,     6,    36,    19,    20,    21,    22,    23,
    24,    25,    33,    36,    28,    29,     5,     6,     7,     8,
     9,    10,    11,    26,    24,     0,     1,    67,    17,    25,
     5,    82,    92,    -1,    -1,    24,     1,    26,    27,    -1,
     5,    -1,    31,    32,    19,    20,    21,    22,    23,    24,
    25,    -1,    -1,    -1,    19,    20,    21,    22,    23,    24,
    25,     5,     6,     7,     8,     9,    10,    11,    15,    16,
    -1,    -1,    -1,    17,    -1,    22,    23,    -1,    -1,    -1,
    24,    -1,    26,    27,    12,    13,    14,    -1,    -1,    17,
    18,    12,    13,    14,    -1,    -1,    -1,    18
};
#define YYPURE 1

/* -*-C-*-  Note some compilers choke on comments on `#line' lines.  */
#line 3 "/usr/share/bison.simple"
/* This file comes from bison-1.28.  */

/* Skeleton output parser for bison,
   Copyright (C) 1984, 1989, 1990 Free Software Foundation, Inc.

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2, or (at your option)
   any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 59 Temple Place - Suite 330,
   Boston, MA 02111-1307, USA.  */

/* As a special exception, when this file is copied by Bison into a
   Bison output file, you may use that output file without restriction.
   This special exception was added by the Free Software Foundation
   in version 1.24 of Bison.  */

/* This is the parser code that is written into each bison parser
  when the %semantic_parser declaration is not specified in the grammar.
  It was written by Richard Stallman by simplifying the hairy parser
  used when %semantic_parser is specified.  */

#ifndef YYPARSE_RETURN_TYPE
#define YYPARSE_RETURN_TYPE int
#endif


#ifndef YYSTACK_USE_ALLOCA
#ifdef alloca
#define YYSTACK_USE_ALLOCA
#else /* alloca not defined */
#ifdef __GNUC__
#define YYSTACK_USE_ALLOCA
#define alloca __builtin_alloca
#else /* not GNU C.  */
#if (!defined (__STDC__) && defined (sparc)) || defined (__sparc__) || defined (__sparc) || defined (__sgi) || (defined (__sun) && defined (__i386))
#define YYSTACK_USE_ALLOCA
#include <alloca.h>
#else /* not sparc */
/* We think this test detects Watcom and Microsoft C.  */
/* This used to test MSDOS, but that is a bad idea
   since that symbol is in the user namespace.  */
#if (defined (_MSDOS) || defined (_MSDOS_)) && !defined (__TURBOC__)
#if 0 /* No need for malloc.h, which pollutes the namespace;
	 instead, just don't use alloca.  */
#include <malloc.h>
#endif
#else /* not MSDOS, or __TURBOC__ */
#if defined(_AIX)
/* I don't know what this was needed for, but it pollutes the namespace.
   So I turned it off.   rms, 2 May 1997.  */
/* #include <malloc.h>  */
 #pragma alloca
#define YYSTACK_USE_ALLOCA
#else /* not MSDOS, or __TURBOC__, or _AIX */
#if 0
#ifdef __hpux /* haible@ilog.fr says this works for HPUX 9.05 and up,
		 and on HPUX 10.  Eventually we can turn this on.  */
#define YYSTACK_USE_ALLOCA
#define alloca __builtin_alloca
#endif /* __hpux */
#endif
#endif /* not _AIX */
#endif /* not MSDOS, or __TURBOC__ */
#endif /* not sparc */
#endif /* not GNU C */
#endif /* alloca not defined */
#endif /* YYSTACK_USE_ALLOCA not defined */

#ifdef YYSTACK_USE_ALLOCA
#define YYSTACK_ALLOC alloca
#else
#define YYSTACK_ALLOC malloc
#endif

/* Note: there must be only one dollar sign in this file.
   It is replaced by the list of actions, each action
   as one case of the switch.  */

#define yyerrok		(yyerrstatus = 0)
#define yyclearin	(yychar = YYEMPTY)
#define YYEMPTY		-2
#define YYEOF		0
#define YYACCEPT	goto yyacceptlab
#define YYABORT 	goto yyabortlab
#define YYERROR		goto yyerrlab1
/* Like YYERROR except do call yyerror.
   This remains here temporarily to ease the
   transition to the new meaning of YYERROR, for GCC.
   Once GCC version 2 has supplanted version 1, this can go.  */
#define YYFAIL		goto yyerrlab
#define YYRECOVERING()  (!!yyerrstatus)
#define YYBACKUP(token, value) \
do								\
  if (yychar == YYEMPTY && yylen == 1)				\
    { yychar = (token), yylval = (value);			\
      yychar1 = YYTRANSLATE (yychar);				\
      YYPOPSTACK;						\
      goto yybackup;						\
    }								\
  else								\
    { yyerror ("syntax error: cannot back up"); YYERROR; }	\
while (0)

#define YYTERROR	1
#define YYERRCODE	256

#ifndef YYPURE
#define YYLEX		yylex()
#endif

#ifdef YYPURE
#ifdef YYLSP_NEEDED
#ifdef YYLEX_PARAM
#define YYLEX		yylex(&yylval, &yylloc, YYLEX_PARAM)
#else
#define YYLEX		yylex(&yylval, &yylloc)
#endif
#else /* not YYLSP_NEEDED */
#ifdef YYLEX_PARAM
#define YYLEX		yylex(&yylval, YYLEX_PARAM)
#else
#define YYLEX		yylex(&yylval)
#endif
#endif /* not YYLSP_NEEDED */
#endif

/* If nonreentrant, generate the variables here */

#ifndef YYPURE

int	yychar;			/*  the lookahead symbol		*/
YYSTYPE	yylval;			/*  the semantic value of the		*/
				/*  lookahead symbol			*/

#ifdef YYLSP_NEEDED
YYLTYPE yylloc;			/*  location data for the lookahead	*/
				/*  symbol				*/
#endif

int yynerrs;			/*  number of parse errors so far       */
#endif  /* not YYPURE */

#if YYDEBUG != 0
int yydebug;			/*  nonzero means print parse trace	*/
/* Since this is uninitialized, it does not stop multiple parsers
   from coexisting.  */
#endif

/*  YYINITDEPTH indicates the initial size of the parser's stacks	*/

#ifndef	YYINITDEPTH
#define YYINITDEPTH 200
#endif

/*  YYMAXDEPTH is the maximum size the stacks can grow to
    (effective only if the built-in stack extension method is used).  */

#if YYMAXDEPTH == 0
#undef YYMAXDEPTH
#endif

#ifndef YYMAXDEPTH
#define YYMAXDEPTH 10000
#endif

/* Define __yy_memcpy.  Note that the size argument
   should be passed with type unsigned int, because that is what the non-GCC
   definitions require.  With GCC, __builtin_memcpy takes an arg
   of type size_t, but it can handle unsigned int.  */

#if __GNUC__ > 1		/* GNU C and GNU C++ define this.  */
#define __yy_memcpy(TO,FROM,COUNT)	__builtin_memcpy(TO,FROM,COUNT)
#else				/* not GNU C or C++ */
#ifndef __cplusplus

/* This is the most reliable way to avoid incompatibilities
   in available built-in functions on various systems.  */
static void
__yy_memcpy (to, from, count)
     char *to;
     char *from;
     unsigned int count;
{
  register char *f = from;
  register char *t = to;
  register int i = count;

  while (i-- > 0)
    *t++ = *f++;
}

#else /* __cplusplus */

/* This is the most reliable way to avoid incompatibilities
   in available built-in functions on various systems.  */
static void
__yy_memcpy (char *to, char *from, unsigned int count)
{
  register char *t = to;
  register char *f = from;
  register int i = count;

  while (i-- > 0)
    *t++ = *f++;
}

#endif
#endif

#line 222 "/usr/share/bison.simple"

/* The user can define YYPARSE_PARAM as the name of an argument to be passed
   into yyparse.  The argument should have type void *.
   It should actually point to an object.
   Grammar actions can access the variable by casting it
   to the proper pointer type.  */

#ifdef YYPARSE_PARAM
#ifdef __cplusplus
#define YYPARSE_PARAM_ARG void *YYPARSE_PARAM
#define YYPARSE_PARAM_DECL
#else /* not __cplusplus */
#define YYPARSE_PARAM_ARG YYPARSE_PARAM
#define YYPARSE_PARAM_DECL void *YYPARSE_PARAM;
#endif /* not __cplusplus */
#else /* not YYPARSE_PARAM */
#define YYPARSE_PARAM_ARG
#define YYPARSE_PARAM_DECL
#endif /* not YYPARSE_PARAM */

/* Prevent warning if -Wstrict-prototypes.  */
#ifdef __GNUC__
#ifdef YYPARSE_PARAM
YYPARSE_RETURN_TYPE
yyparse (void *);
#else
YYPARSE_RETURN_TYPE
yyparse (void);
#endif
#endif

YYPARSE_RETURN_TYPE
yyparse(YYPARSE_PARAM_ARG)
     YYPARSE_PARAM_DECL
{
  register int yystate;
  register int yyn;
  register short *yyssp;
  register YYSTYPE *yyvsp;
  int yyerrstatus;	/*  number of tokens to shift before error messages enabled */
  int yychar1 = 0;		/*  lookahead token as an internal (translated) token number */

  short	yyssa[YYINITDEPTH];	/*  the state stack			*/
  YYSTYPE yyvsa[YYINITDEPTH];	/*  the semantic value stack		*/

  short *yyss = yyssa;		/*  refer to the stacks thru separate pointers */
  YYSTYPE *yyvs = yyvsa;	/*  to allow yyoverflow to reallocate them elsewhere */

#ifdef YYLSP_NEEDED
  YYLTYPE yylsa[YYINITDEPTH];	/*  the location stack			*/
  YYLTYPE *yyls = yylsa;
  YYLTYPE *yylsp;

#define YYPOPSTACK   (yyvsp--, yyssp--, yylsp--)
#else
#define YYPOPSTACK   (yyvsp--, yyssp--)
#endif

  int yystacksize = YYINITDEPTH;
#ifndef YYSTACK_USE_ALLOCA
  int yyfree_stacks = 0;
#endif

#ifdef YYPURE
  int yychar;
  YYSTYPE yylval;
  int yynerrs;
#ifdef YYLSP_NEEDED
  YYLTYPE yylloc;
#endif
#endif

  YYSTYPE yyval;		/*  the variable used to return		*/
				/*  semantic values from the action	*/
				/*  routines				*/

  int yylen;

#if YYDEBUG != 0
  if (yydebug)
    fprintf(stderr, "Starting parse\n");
#endif

  yystate = 0;
  yyerrstatus = 0;
  yynerrs = 0;
  yychar = YYEMPTY;		/* Cause a token to be read.  */

  /* Initialize stack pointers.
     Waste one element of value and location stack
     so that they stay on the same level as the state stack.
     The wasted elements are never initialized.  */

  yyssp = yyss - 1;
  yyvsp = yyvs;
#ifdef YYLSP_NEEDED
  yylsp = yyls;
#endif

/* Push a new state, which is found in  yystate  .  */
/* In all cases, when you get here, the value and location stacks
   have just been pushed. so pushing a state here evens the stacks.  */
yynewstate:

  *++yyssp = yystate;

  if (yyssp >= yyss + yystacksize - 1)
    {
      /* Give user a chance to reallocate the stack */
      /* Use copies of these so that the &'s don't force the real ones into memory. */
      YYSTYPE *yyvs1 = yyvs;
      short *yyss1 = yyss;
#ifdef YYLSP_NEEDED
      YYLTYPE *yyls1 = yyls;
#endif

      /* Get the current used size of the three stacks, in elements.  */
      int size = yyssp - yyss + 1;

#ifdef yyoverflow
      /* Each stack pointer address is followed by the size of
	 the data in use in that stack, in bytes.  */
#ifdef YYLSP_NEEDED
      /* This used to be a conditional around just the two extra args,
	 but that might be undefined if yyoverflow is a macro.  */
      yyoverflow("parser stack overflow",
		 &yyss1, size * sizeof (*yyssp),
		 &yyvs1, size * sizeof (*yyvsp),
		 &yyls1, size * sizeof (*yylsp),
		 &yystacksize);
#else
      yyoverflow("parser stack overflow",
		 &yyss1, size * sizeof (*yyssp),
		 &yyvs1, size * sizeof (*yyvsp),
		 &yystacksize);
#endif

      yyss = yyss1; yyvs = yyvs1;
#ifdef YYLSP_NEEDED
      yyls = yyls1;
#endif
#else /* no yyoverflow */
      /* Extend the stack our own way.  */
      if (yystacksize >= YYMAXDEPTH)
	{
	  yyerror("parser stack overflow");
#ifndef YYSTACK_USE_ALLOCA
	  if (yyfree_stacks)
	    {
	      free (yyss);
	      free (yyvs);
#ifdef YYLSP_NEEDED
	      free (yyls);
#endif
	    }
#endif	    
	  return 2;
	}
      yystacksize *= 2;
      if (yystacksize > YYMAXDEPTH)
	yystacksize = YYMAXDEPTH;
#ifndef YYSTACK_USE_ALLOCA
      yyfree_stacks = 1;
#endif
      yyss = (short *) YYSTACK_ALLOC (yystacksize * sizeof (*yyssp));
      __yy_memcpy ((char *)yyss, (char *)yyss1,
		   size * (unsigned int) sizeof (*yyssp));
      yyvs = (YYSTYPE *) YYSTACK_ALLOC (yystacksize * sizeof (*yyvsp));
      __yy_memcpy ((char *)yyvs, (char *)yyvs1,
		   size * (unsigned int) sizeof (*yyvsp));
#ifdef YYLSP_NEEDED
      yyls = (YYLTYPE *) YYSTACK_ALLOC (yystacksize * sizeof (*yylsp));
      __yy_memcpy ((char *)yyls, (char *)yyls1,
		   size * (unsigned int) sizeof (*yylsp));
#endif
#endif /* no yyoverflow */

      yyssp = yyss + size - 1;
      yyvsp = yyvs + size - 1;
#ifdef YYLSP_NEEDED
      yylsp = yyls + size - 1;
#endif

#if YYDEBUG != 0
      if (yydebug)
	fprintf(stderr, "Stack size increased to %d\n", yystacksize);
#endif

      if (yyssp >= yyss + yystacksize - 1)
	YYABORT;
    }

#if YYDEBUG != 0
  if (yydebug)
    fprintf(stderr, "Entering state %d\n", yystate);
#endif

  goto yybackup;
 yybackup:

/* Do appropriate processing given the current state.  */
/* Read a lookahead token if we need one and don't already have one.  */
/* yyresume: */

  /* First try to decide what to do without reference to lookahead token.  */

  yyn = yypact[yystate];
  if (yyn == YYFLAG)
    goto yydefault;

  /* Not known => get a lookahead token if don't already have one.  */

  /* yychar is either YYEMPTY or YYEOF
     or a valid token in external form.  */

  if (yychar == YYEMPTY)
    {
#if YYDEBUG != 0
      if (yydebug)
	fprintf(stderr, "Reading a token: ");
#endif
      yychar = YYLEX;
    }

  /* Convert token to internal form (in yychar1) for indexing tables with */

  if (yychar <= 0)		/* This means end of input. */
    {
      yychar1 = 0;
      yychar = YYEOF;		/* Don't call YYLEX any more */

#if YYDEBUG != 0
      if (yydebug)
	fprintf(stderr, "Now at end of input.\n");
#endif
    }
  else
    {
      yychar1 = YYTRANSLATE(yychar);

#if YYDEBUG != 0
      if (yydebug)
	{
	  fprintf (stderr, "Next token is %d (%s", yychar, yytname[yychar1]);
	  /* Give the individual parser a way to print the precise meaning
	     of a token, for further debugging info.  */
#ifdef YYPRINT
	  YYPRINT (stderr, yychar, yylval);
#endif
	  fprintf (stderr, ")\n");
	}
#endif
    }

  yyn += yychar1;
  if (yyn < 0 || yyn > YYLAST || yycheck[yyn] != yychar1)
    goto yydefault;

  yyn = yytable[yyn];

  /* yyn is what to do for this token type in this state.
     Negative => reduce, -yyn is rule number.
     Positive => shift, yyn is new state.
       New state is final state => don't bother to shift,
       just return success.
     0, or most negative number => error.  */

  if (yyn < 0)
    {
      if (yyn == YYFLAG)
	goto yyerrlab;
      yyn = -yyn;
      goto yyreduce;
    }
  else if (yyn == 0)
    goto yyerrlab;

  if (yyn == YYFINAL)
    YYACCEPT;

  /* Shift the lookahead token.  */

#if YYDEBUG != 0
  if (yydebug)
    fprintf(stderr, "Shifting token %d (%s), ", yychar, yytname[yychar1]);
#endif

  /* Discard the token being shifted unless it is eof.  */
  if (yychar != YYEOF)
    yychar = YYEMPTY;

  *++yyvsp = yylval;
#ifdef YYLSP_NEEDED
  *++yylsp = yylloc;
#endif

  /* count tokens shifted since error; after three, turn off error status.  */
  if (yyerrstatus) yyerrstatus--;

  yystate = yyn;
  goto yynewstate;

/* Do the default action for the current state.  */
yydefault:

  yyn = yydefact[yystate];
  if (yyn == 0)
    goto yyerrlab;

/* Do a reduction.  yyn is the number of a rule to reduce with.  */
yyreduce:
  yylen = yyr2[yyn];
  if (yylen > 0)
    yyval = yyvsp[1-yylen]; /* implement default value of the action */

#if YYDEBUG != 0
  if (yydebug)
    {
      int i;

      fprintf (stderr, "Reducing via rule %d (line %d), ",
	       yyn, yyrline[yyn]);

      /* Print the symbols being reduced, and their result.  */
      for (i = yyprhs[yyn]; yyrhs[i] > 0; i++)
	fprintf (stderr, "%s ", yytname[yyrhs[i]]);
      fprintf (stderr, " -> %s\n", yytname[yyr1[yyn]]);
    }
#endif


  switch (yyn) {

case 1:
#line 96 "css_syntax.y"
{
						*(struct selector_list_t**) yyparam = yyvsp[0].selector_list;
					;
    break;}
case 2:
#line 102 "css_syntax.y"
{
								struct selector_list_t *pos = yyvsp[-2].selector_list;
								if (pos != NULL) {
									while (pos->next != NULL) {
										pos = pos->next;
									}
									pos->next = yyvsp[0].selector_list;
								} else {
									yyvsp[-2].selector_list = yyvsp[0].selector_list;
								}
								yyval.selector_list = yyvsp[-2].selector_list;
							;
    break;}
case 3:
#line 114 "css_syntax.y"
{ yyval.selector_list = NULL;  ;
    break;}
case 11:
#line 137 "css_syntax.y"
{ yyval.lexeme = yyvsp[0].lexeme; ;
    break;}
case 12:
#line 138 "css_syntax.y"
{
			char *begin = yyvsp[0].lexeme;
			char *end = yyvsp[0].lexeme + strlen(yyvsp[0].lexeme);

			/* Skip url( */
			begin += 4;
			/* skip whitespace */
			while (*begin == ' ')
				++begin;

			/* Skip ) */
			end -= 2;
			/* skip whitespace */
			while (*end == ' ')
				--end;

			end[1] = 0;

			yyval.lexeme = strdup(begin);
			free(yyvsp[0].lexeme);
		;
    break;}
case 13:
#line 162 "css_syntax.y"
{ yyval.letter = '-'; ;
    break;}
case 14:
#line 163 "css_syntax.y"
{ yyval.letter = '+'; ;
    break;}
case 15:
#line 167 "css_syntax.y"
{ yyval.letter = '/'; ;
    break;}
case 16:
#line 168 "css_syntax.y"
{ yyval.letter = ','; ;
    break;}
case 17:
#line 169 "css_syntax.y"
{yyval.letter = ' '; ;
    break;}
case 18:
#line 173 "css_syntax.y"
{ yyval.lexeme = yyvsp[0].lexeme; ;
    break;}
case 19:
#line 177 "css_syntax.y"
{
										struct selector_list_t *pos = yyvsp[-3].selector_list;
										while (pos != NULL) {
											struct property_t *i = yyvsp[-1].property;
											while (i != NULL) {
												i->count++;
												i = i->next;
											}
											pos->selector->property = yyvsp[-1].property;
											pos = pos->next;
										}
										yyval.selector_list = yyvsp[-3].selector_list;
									;
    break;}
case 20:
#line 193 "css_syntax.y"
{
				if (yyvsp[0].selector != NULL) {
					yyval.selector_list = (struct selector_list_t*)
						malloc (sizeof(struct selector_list_t));
					yyval.selector_list->selector = yyvsp[0].selector;
					yyval.selector_list->next = NULL;
				} else {
					yyval.selector_list = NULL;
				}
			;
    break;}
case 21:
#line 203 "css_syntax.y"
{
								if (yyvsp[-2].selector != NULL) {
									struct selector_list_t *new;
									new = (struct selector_list_t*)
										malloc (sizeof(struct selector_list_t));
									new->selector = yyvsp[-2].selector;
									new->next = yyvsp[0].selector_list;
									yyval.selector_list = new;
								} else {
									yyval.selector_list = yyvsp[0].selector_list;
								}
							;
    break;}
case 22:
#line 218 "css_syntax.y"
{
									yyval.property = yyvsp[0].property;
								;
    break;}
case 23:
#line 221 "css_syntax.y"
{
									if (yyvsp[-2].property != NULL) {
										yyvsp[-2].property->next = yyvsp[0].property;
										yyval.property = yyvsp[-2].property;
									} else {
										yyval.property = yyvsp[0].property;
									}
								;
    break;}
case 24:
#line 232 "css_syntax.y"
{
										struct selector_t *pos = yyvsp[-1].selector;
										while (pos->next != NULL) {
											pos = pos->next;
										}
										pos->pseudo_element = yyvsp[0].pseudo_element;
										yyval.selector = yyvsp[-1].selector;
									;
    break;}
case 25:
#line 240 "css_syntax.y"
{
												struct selector_t *pos = yyvsp[-1].selector;
												while (pos->next != NULL) {
													pos = pos->next;
												}
												pos->next = yyvsp[0].selector;
												yyval.selector = yyvsp[-1].selector;
											;
    break;}
case 26:
#line 248 "css_syntax.y"
{ yyval.selector = yyvsp[0].selector; ;
    break;}
case 27:
#line 249 "css_syntax.y"
{ yyval.selector = yyvsp[0].selector; ;
    break;}
case 28:
#line 250 "css_syntax.y"
{ yyval.selector = NULL; ;
    break;}
case 29:
#line 251 "css_syntax.y"
{ yyval.selector = NULL; ;
    break;}
case 30:
#line 255 "css_syntax.y"
{ yyval.selector = yyvsp[0].selector; ;
    break;}
case 31:
#line 256 "css_syntax.y"
{
										yyvsp[-1].selector->next = yyvsp[0].selector;
										yyval.selector = yyvsp[-1].selector;
									;
    break;}
case 32:
#line 269 "css_syntax.y"
{
										yyval.selector = (struct selector_t*)
											malloc(sizeof(struct selector_t));
										yyval.selector->element_name = yyvsp[-3].lexeme;
										yyval.selector->id = yyvsp[-2].lexeme;
										yyval.selector->e_class = yyvsp[-1].lexeme;
										yyval.selector->pseudo_class = yyvsp[0].pseudo_class;
										yyval.selector->pseudo_element = 0;
										yyval.selector->next = NULL;
									 ;
    break;}
case 33:
#line 279 "css_syntax.y"
{
									yyval.selector = (struct selector_t*)
										malloc(sizeof(struct selector_t));
									yyval.selector->element_name = yyvsp[-2].lexeme;
									yyval.selector->id = yyvsp[-1].lexeme;
									yyval.selector->e_class = NULL;
									yyval.selector->pseudo_class = yyvsp[0].pseudo_class;
									yyval.selector->pseudo_element = 0;
									yyval.selector->next = NULL;
								;
    break;}
case 34:
#line 289 "css_syntax.y"
{
										yyval.selector = (struct selector_t*)
											malloc(sizeof(struct selector_t));
										yyval.selector->element_name = yyvsp[-2].lexeme;
										yyval.selector->id = NULL;
										yyval.selector->e_class = yyvsp[-1].lexeme;
										yyval.selector->pseudo_class = yyvsp[0].pseudo_class;
										yyval.selector->pseudo_element = 0;
										yyval.selector->next = NULL;
									;
    break;}
case 35:
#line 299 "css_syntax.y"
{
							yyval.selector = (struct selector_t*)
								malloc(sizeof(struct selector_t));
							yyval.selector->element_name = yyvsp[-2].lexeme;
							yyval.selector->id = yyvsp[-1].lexeme;
							yyval.selector->e_class = yyvsp[0].lexeme;
							yyval.selector->pseudo_class = 0;
							yyval.selector->pseudo_element = 0;
							yyval.selector->next = NULL;
						;
    break;}
case 36:
#line 309 "css_syntax.y"
{
						yyval.selector = (struct selector_t*)
							malloc(sizeof(struct selector_t));
						yyval.selector->element_name = yyvsp[-1].lexeme;
						yyval.selector->id = yyvsp[0].lexeme;
						yyval.selector->e_class = NULL;
						yyval.selector->pseudo_class = 0;
						yyval.selector->pseudo_element = 0;
						yyval.selector->next = NULL;
					;
    break;}
case 37:
#line 319 "css_syntax.y"
{
							yyval.selector = (struct selector_t*)
								malloc(sizeof(struct selector_t));
							yyval.selector->element_name = yyvsp[-1].lexeme;
							yyval.selector->id = NULL;
							yyval.selector->e_class = yyvsp[0].lexeme;
							yyval.selector->pseudo_class = 0;
							yyval.selector->pseudo_element = 0;
							yyval.selector->next = NULL;
						;
    break;}
case 38:
#line 329 "css_syntax.y"
{
								yyval.selector = (struct selector_t*)
									malloc(sizeof(struct selector_t));
								yyval.selector->element_name = yyvsp[-1].lexeme;
								yyval.selector->id = NULL;
								yyval.selector->e_class = NULL;
								yyval.selector->pseudo_class = yyvsp[0].pseudo_class;
								yyval.selector->pseudo_element = 0;
								yyval.selector->next = NULL;
							;
    break;}
case 39:
#line 339 "css_syntax.y"
{
					yyval.selector = (struct selector_t*)
						malloc(sizeof(struct selector_t));
					yyval.selector->element_name = yyvsp[0].lexeme;
					yyval.selector->id = NULL;
					yyval.selector->e_class = NULL;
					yyval.selector->pseudo_class = 0;
					yyval.selector->pseudo_element = 0;
					yyval.selector->next = NULL;
				;
    break;}
case 40:
#line 349 "css_syntax.y"
{
										yyval.selector = (struct selector_t*)
											malloc(sizeof(struct selector_t));
										yyval.selector->element_name = NULL;
										yyval.selector->id = yyvsp[-2].lexeme;
										yyval.selector->e_class = yyvsp[-1].lexeme;
										yyval.selector->pseudo_class = yyvsp[0].pseudo_class;
										yyval.selector->pseudo_element = 0;
										yyval.selector->next = NULL;
									;
    break;}
case 41:
#line 359 "css_syntax.y"
{
						yyval.selector = (struct selector_t*)
							malloc(sizeof(struct selector_t));
						yyval.selector->element_name = NULL;
						yyval.selector->id = yyvsp[-1].lexeme;
						yyval.selector->e_class = yyvsp[0].lexeme;
						yyval.selector->pseudo_class = 0;
						yyval.selector->pseudo_element = 0;
						yyval.selector->next = NULL;
					;
    break;}
case 42:
#line 369 "css_syntax.y"
{
								yyval.selector = (struct selector_t*)
									malloc(sizeof(struct selector_t));
								yyval.selector->element_name = NULL;
								yyval.selector->id = yyvsp[-1].lexeme;
								yyval.selector->e_class = NULL;
								yyval.selector->pseudo_class = yyvsp[0].pseudo_class;
								yyval.selector->pseudo_element = 0;
								yyval.selector->next = NULL;
							;
    break;}
case 43:
#line 379 "css_syntax.y"
{
					yyval.selector = (struct selector_t*)
						malloc(sizeof(struct selector_t));
					yyval.selector->element_name = NULL;
					yyval.selector->id = yyvsp[0].lexeme;
					yyval.selector->e_class = NULL;
					yyval.selector->pseudo_class = 0;
					yyval.selector->pseudo_element = 0;
					yyval.selector->next = NULL;
				;
    break;}
case 44:
#line 389 "css_syntax.y"
{
									yyval.selector = (struct selector_t*)
										malloc(sizeof(struct selector_t));
									yyval.selector->element_name = NULL;
									yyval.selector->id = NULL;
									yyval.selector->e_class = yyvsp[-1].lexeme;
									yyval.selector->pseudo_class = yyvsp[0].pseudo_class;
									yyval.selector->pseudo_element = 0;
									yyval.selector->next = NULL;
								;
    break;}
case 45:
#line 399 "css_syntax.y"
{
						yyval.selector = (struct selector_t*)
							malloc(sizeof(struct selector_t));
						yyval.selector->element_name = NULL;
						yyval.selector->id = NULL;
						yyval.selector->e_class = yyvsp[0].lexeme;
						yyval.selector->pseudo_class = 0;
						yyval.selector->pseudo_element = 0;
						yyval.selector->next = NULL;
					;
    break;}
case 46:
#line 409 "css_syntax.y"
{
							yyval.selector = (struct selector_t*)
								malloc(sizeof(struct selector_t));
							yyval.selector->element_name = NULL;
							yyval.selector->id = NULL;
							yyval.selector->e_class = NULL;
							yyval.selector->pseudo_class = yyvsp[0].pseudo_class;
							yyval.selector->pseudo_element = 0;
							yyval.selector->next = NULL;
						;
    break;}
case 47:
#line 422 "css_syntax.y"
{ yyval.lexeme = yyvsp[0].lexeme; ;
    break;}
case 48:
#line 426 "css_syntax.y"
{ yyval.pseudo_class = PS_CLASS_LINK; ;
    break;}
case 49:
#line 427 "css_syntax.y"
{ yyval.pseudo_class = PS_CLASS_VISITED; ;
    break;}
case 50:
#line 428 "css_syntax.y"
{ yyval.pseudo_class = PS_CLASS_ACTIVE; ;
    break;}
case 51:
#line 432 "css_syntax.y"
{ yyval.pseudo_class = PS_CLASS_LINK; ;
    break;}
case 52:
#line 433 "css_syntax.y"
{ yyval.pseudo_class = PS_CLASS_VISITED; ;
    break;}
case 53:
#line 434 "css_syntax.y"
{ yyval.pseudo_class = PS_CLASS_ACTIVE; ;
    break;}
case 54:
#line 438 "css_syntax.y"
{ yyval.lexeme = yyvsp[0].lexeme; ;
    break;}
case 55:
#line 442 "css_syntax.y"
{ yyval.lexeme = yyvsp[0].lexeme; ;
    break;}
case 56:
#line 446 "css_syntax.y"
{ yyval.pseudo_element = PS_ELEMENT_FIRST_LETTER; ;
    break;}
case 57:
#line 447 "css_syntax.y"
{ yyval.pseudo_element = PS_ELEMENT_FIRST_LINE; ;
    break;}
case 58:
#line 451 "css_syntax.y"
{
					yyval.selector = (struct selector_t*)
						malloc(sizeof(struct selector_t));
					yyval.selector->element_name = NULL;
					yyval.selector->id = NULL;
					yyval.selector->e_class = NULL;
					yyval.selector->pseudo_class = 0;
					yyval.selector->pseudo_element = PS_ELEMENT_FIRST_LETTER;
					yyval.selector->next = NULL;
				;
    break;}
case 59:
#line 461 "css_syntax.y"
{
					yyval.selector = (struct selector_t*)
						malloc(sizeof(struct selector_t));
					yyval.selector->element_name = NULL;
					yyval.selector->id = NULL;
					yyval.selector->e_class = NULL;
					yyval.selector->pseudo_class = 0;
					yyval.selector->pseudo_element = PS_ELEMENT_FIRST_LINE;
					yyval.selector->next = NULL;
				;
    break;}
case 60:
#line 478 "css_syntax.y"
{ yyval.lexeme = yyvsp[0].lexeme; ;
    break;}
case 61:
#line 482 "css_syntax.y"
{ yyval.lexeme = yyvsp[0].lexeme; ;
    break;}
case 62:
#line 486 "css_syntax.y"
{
								yyval.property = (struct property_t*)
									malloc(sizeof(struct property_t));
								yyval.property->name = yyvsp[-3].lexeme;
								yyval.property->val = yyvsp[-1].lexeme;
								yyval.property->important = 1;
								yyval.property->count = 0;
								yyval.property->next = NULL;
							;
    break;}
case 63:
#line 495 "css_syntax.y"
{
								yyval.property = (struct property_t*)
									malloc(sizeof(struct property_t));
								yyval.property->name = yyvsp[-2].lexeme;
								yyval.property->val = yyvsp[0].lexeme;
								yyval.property->important = 0;
								yyval.property->count = 0;
								yyval.property->next = NULL;
							;
    break;}
case 64:
#line 504 "css_syntax.y"
{ yyval.property = NULL; ;
    break;}
case 65:
#line 505 "css_syntax.y"
{
								yyval.property = NULL;
							;
    break;}
case 66:
#line 511 "css_syntax.y"
{ ;
    break;}
case 67:
#line 515 "css_syntax.y"
{ yyval.lexeme = yyvsp[0].lexeme; ;
    break;}
case 68:
#line 516 "css_syntax.y"
{
							char *s = (char*) malloc (strlen(yyvsp[-2].lexeme)+strlen(yyvsp[0].lexeme)+2);
							strcpy(s, yyvsp[-2].lexeme);
							s[strlen(s)+1] = 0;
							s[strlen(s)] = yyvsp[-1].letter;
							strcat(s, yyvsp[0].lexeme);
							free(yyvsp[-2].lexeme);
							free(yyvsp[0].lexeme);
							yyval.lexeme = s;
						;
    break;}
case 69:
#line 529 "css_syntax.y"
{
							char *s = (char*) malloc(strlen(yyvsp[0].lexeme)+2);
							s[0] = yyvsp[-1].letter;
							s[1] = 0;
							strcat(s, yyvsp[0].lexeme);
							free(yyvsp[0].lexeme);
							yyval.lexeme = s;
						;
    break;}
case 70:
#line 537 "css_syntax.y"
{ yyval.lexeme = yyvsp[0].lexeme; ;
    break;}
case 71:
#line 541 "css_syntax.y"
{ yyval.lexeme = yyvsp[0].lexeme; ;
    break;}
case 72:
#line 542 "css_syntax.y"
{ yyval.lexeme = yyvsp[0].lexeme; ;
    break;}
case 73:
#line 543 "css_syntax.y"
{ yyval.lexeme = yyvsp[0].lexeme; ;
    break;}
case 74:
#line 544 "css_syntax.y"
{ yyval.lexeme = yyvsp[0].lexeme; ;
    break;}
case 75:
#line 545 "css_syntax.y"
{ yyval.lexeme = yyvsp[0].lexeme; ;
    break;}
case 76:
#line 546 "css_syntax.y"
{ yyval.lexeme = yyvsp[0].lexeme; ;
    break;}
case 77:
#line 547 "css_syntax.y"
{ yyval.lexeme = yyvsp[0].lexeme; ;
    break;}
case 78:
#line 548 "css_syntax.y"
{ yyval.lexeme = yyvsp[0].lexeme; ;
    break;}
case 79:
#line 549 "css_syntax.y"
{ yyval.lexeme = yyvsp[0].lexeme; ;
    break;}
case 80:
#line 550 "css_syntax.y"
{ yyval.lexeme = yyvsp[0].lexeme; ;
    break;}
case 81:
#line 558 "css_syntax.y"
{ 
			yyval.lexeme = (char*) malloc (strlen(yyvsp[0].lexeme)+2);
			sprintf(yyval.lexeme, "#%s", yyvsp[0].lexeme);
			free(yyvsp[0].lexeme);
		;
    break;}
case 82:
#line 563 "css_syntax.y"
{ 
						yyval.lexeme = (char*) malloc (strlen(yyvsp[0].lexeme)+2);
						sprintf(yyval.lexeme, "#%s", yyvsp[0].lexeme);
						free(yyvsp[0].lexeme);
					;
    break;}
}
   /* the action file gets copied in in place of this dollarsign */
#line 554 "/usr/share/bison.simple"

  yyvsp -= yylen;
  yyssp -= yylen;
#ifdef YYLSP_NEEDED
  yylsp -= yylen;
#endif

#if YYDEBUG != 0
  if (yydebug)
    {
      short *ssp1 = yyss - 1;
      fprintf (stderr, "state stack now");
      while (ssp1 != yyssp)
	fprintf (stderr, " %d", *++ssp1);
      fprintf (stderr, "\n");
    }
#endif

  *++yyvsp = yyval;

#ifdef YYLSP_NEEDED
  yylsp++;
  if (yylen == 0)
    {
      yylsp->first_line = yylloc.first_line;
      yylsp->first_column = yylloc.first_column;
      yylsp->last_line = (yylsp-1)->last_line;
      yylsp->last_column = (yylsp-1)->last_column;
      yylsp->text = 0;
    }
  else
    {
      yylsp->last_line = (yylsp+yylen-1)->last_line;
      yylsp->last_column = (yylsp+yylen-1)->last_column;
    }
#endif

  /* Now "shift" the result of the reduction.
     Determine what state that goes to,
     based on the state we popped back to
     and the rule number reduced by.  */

  yyn = yyr1[yyn];

  yystate = yypgoto[yyn - YYNTBASE] + *yyssp;
  if (yystate >= 0 && yystate <= YYLAST && yycheck[yystate] == *yyssp)
    yystate = yytable[yystate];
  else
    yystate = yydefgoto[yyn - YYNTBASE];

  goto yynewstate;

yyerrlab:   /* here on detecting error */

  if (! yyerrstatus)
    /* If not already recovering from an error, report this error.  */
    {
      ++yynerrs;

#ifdef YYERROR_VERBOSE
      yyn = yypact[yystate];

      if (yyn > YYFLAG && yyn < YYLAST)
	{
	  int size = 0;
	  char *msg;
	  int x, count;

	  count = 0;
	  /* Start X at -yyn if nec to avoid negative indexes in yycheck.  */
	  for (x = (yyn < 0 ? -yyn : 0);
	       x < (sizeof(yytname) / sizeof(char *)); x++)
	    if (yycheck[x + yyn] == x)
	      size += strlen(yytname[x]) + 15, count++;
	  msg = (char *) malloc(size + 15);
	  if (msg != 0)
	    {
	      strcpy(msg, "parse error");

	      if (count < 5)
		{
		  count = 0;
		  for (x = (yyn < 0 ? -yyn : 0);
		       x < (sizeof(yytname) / sizeof(char *)); x++)
		    if (yycheck[x + yyn] == x)
		      {
			strcat(msg, count == 0 ? ", expecting `" : " or `");
			strcat(msg, yytname[x]);
			strcat(msg, "'");
			count++;
		      }
		}
	      yyerror(msg);
	      free(msg);
	    }
	  else
	    yyerror ("parse error; also virtual memory exceeded");
	}
      else
#endif /* YYERROR_VERBOSE */
	yyerror("parse error");
    }

  goto yyerrlab1;
yyerrlab1:   /* here on error raised explicitly by an action */

  if (yyerrstatus == 3)
    {
      /* if just tried and failed to reuse lookahead token after an error, discard it.  */

      /* return failure if at end of input */
      if (yychar == YYEOF)
	YYABORT;

#if YYDEBUG != 0
      if (yydebug)
	fprintf(stderr, "Discarding token %d (%s).\n", yychar, yytname[yychar1]);
#endif

      yychar = YYEMPTY;
    }

  /* Else will try to reuse lookahead token
     after shifting the error token.  */

  yyerrstatus = 3;		/* Each real token shifted decrements this */

  goto yyerrhandle;

yyerrdefault:  /* current state does not do anything special for the error token. */

#if 0
  /* This is wrong; only states that explicitly want error tokens
     should shift them.  */
  yyn = yydefact[yystate];  /* If its default is to accept any token, ok.  Otherwise pop it.*/
  if (yyn) goto yydefault;
#endif

yyerrpop:   /* pop the current state because it cannot handle the error token */

  if (yyssp == yyss) YYABORT;
  yyvsp--;
  yystate = *--yyssp;
#ifdef YYLSP_NEEDED
  yylsp--;
#endif

#if YYDEBUG != 0
  if (yydebug)
    {
      short *ssp1 = yyss - 1;
      fprintf (stderr, "Error: state stack now");
      while (ssp1 != yyssp)
	fprintf (stderr, " %d", *++ssp1);
      fprintf (stderr, "\n");
    }
#endif

yyerrhandle:

  yyn = yypact[yystate];
  if (yyn == YYFLAG)
    goto yyerrdefault;

  yyn += YYTERROR;
  if (yyn < 0 || yyn > YYLAST || yycheck[yyn] != YYTERROR)
    goto yyerrdefault;

  yyn = yytable[yyn];
  if (yyn < 0)
    {
      if (yyn == YYFLAG)
	goto yyerrpop;
      yyn = -yyn;
      goto yyreduce;
    }
  else if (yyn == 0)
    goto yyerrpop;

  if (yyn == YYFINAL)
    YYACCEPT;

#if YYDEBUG != 0
  if (yydebug)
    fprintf(stderr, "Shifting error token, ");
#endif

  *++yyvsp = yylval;
#ifdef YYLSP_NEEDED
  *++yylsp = yylloc;
#endif

  yystate = yyn;
  goto yynewstate;

 yyacceptlab:
  /* YYACCEPT comes here.  */
#ifndef YYSTACK_USE_ALLOCA
  if (yyfree_stacks)
    {
      free (yyss);
      free (yyvs);
#ifdef YYLSP_NEEDED
      free (yyls);
#endif
    }
#endif
  return 0;

 yyabortlab:
  /* YYABORT comes here.  */
#ifndef YYSTACK_USE_ALLOCA
  if (yyfree_stacks)
    {
      free (yyss);
      free (yyvs);
#ifdef YYLSP_NEEDED
      free (yyls);
#endif
    }
#endif    
  return 1;
}
#line 570 "css_syntax.y"


int yyerror(char *s) {
	fprintf(stderr, "Error: %s\n", s);
	return 0;
}

struct selector_list_t* css_parse(const char *buffer, int buf_len) {
	struct selector_list_t *ret = NULL;
	//yydebug = 1;
	init_yylex(buffer, buf_len);
	yyparse(&ret);
	end_yylex();
	return ret;
}
