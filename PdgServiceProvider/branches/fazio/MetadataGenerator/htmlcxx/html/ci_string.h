#ifndef __CI_STRING__
#define __CI_STRING__

#include <cstring>
#include <cctype>
#include <string>

#if __GNUC__ >= 3
#include <bits/char_traits.h>
struct ci_char_traits : public std::char_traits<char>
#elif defined(__GNUC__)
#include <std/straits.h>
struct ci_char_traits : public std::string_char_traits<char>
#else 
//Hope string already include it
struct ci_char_traits : public std::char_traits<char>
#endif

// just inherit all the other functions
//  that we don't need to override
{
	static bool eq( char c1, char c2 ) {
		return tolower(c1) == tolower(c2);
	}

	static bool ne( char c1, char c2 ) {
		return tolower(c1) != tolower(c2);
	}

	static bool lt( char c1, char c2 ) {
		return tolower(c1) < tolower(c2);
	}

	static int compare( const char* s1,
			const char* s2,
			size_t n ) {
		return strncasecmp( s1, s2, n );
		// if available on your compiler,
		//  otherwise you can roll your own
	}

	static const char*
		find( const char* s, int n, char a ) {
			while( n-- > 0 && tolower(*s) != tolower(a) ) {
				++s;
			}
			return s;
		}
};

typedef std::basic_string<char, ci_char_traits> ci_string;

#endif
