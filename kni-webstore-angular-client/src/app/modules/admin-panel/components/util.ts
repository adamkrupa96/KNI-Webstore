export class Util {
    static firstUpperLetter(name: string): string {
        name = name.trim().toLowerCase();
        name = name.charAt(0).toUpperCase() + name.slice(1);
        return name;
    }
}
