interface Response {
    token: string;
    user: {
        name: string,
        email: string,
    };
}

export function signIn(token: string, name:  string, email: string): Promise<Response> {
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve({
          token: token, /* "jk12h3j21h3jk212h3jk12h3jkh12j3kh12k123hh21g3f12f3", */
          user: {
            name: name, /* "Thiago", */
            email: email, /* "thiagomarinho@rockeseat.com.br", */
          },
        });
      }, 2000);
    });
  }